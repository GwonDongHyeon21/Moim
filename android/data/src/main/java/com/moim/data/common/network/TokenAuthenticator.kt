package com.moim.data.common.network

import com.moim.data.common.source.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : Authenticator {

    private val reissueClient by lazy { OkHttpClient() }

    override fun authenticate(route: Route?, response: Response): Request? {
        val oldAccessToken = response.request.header("Authorization")?.removePrefix("Bearer ")

        synchronized(this) {
            // ① 다른 스레드에서 이미 토큰을 갱신한 경우 (새 토큰으로 교체 후 재시도)
            val newAccessToken = tokenDataStore.getAccessTokenSync()
            if (oldAccessToken != newAccessToken && !newAccessToken.isNullOrBlank()) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            }

            // ② 리프레시 토큰 확인
            val refreshToken = tokenDataStore.getRefreshTokenSync()
            if (refreshToken.isNullOrBlank()) {
                runBlocking { tokenDataStore.clearTokens() }
                return null
            }

            // ③ 서버에 재발급 API 요청
            val reissueResponse = fetchNewTokens(response.request, refreshToken)

            // ④ 재발급 결과 파싱 및 새 토큰으로 원본 요청 재시도
            if (reissueResponse?.isSuccessful == true) {
                val isSaved = parseAndSaveTokens(reissueResponse)
                reissueResponse.close()

                if (isSaved) {
                    val latestAccessToken = tokenDataStore.getAccessTokenSync()
                    return response.request.newBuilder()
                        .header("Authorization", "Bearer $latestAccessToken")
                        .build()
                }
            }

            // ⑤ 갱신마저 실패 시 (강제 로그아웃)
            reissueResponse?.close()
            runBlocking { tokenDataStore.clearTokens() }

            return null
        }
    }

    private fun fetchNewTokens(originalRequest: Request, refreshToken: String): Response? {
        return try {
            val reissueUrl =
                "${originalRequest.url.scheme}://${originalRequest.url.host}:${originalRequest.url.port}/api/v1/users/reissue"
            val jsonBody = JSONObject().apply { put("refreshToken", refreshToken) }.toString()
            val reissueRequest = Request.Builder()
                .url(reissueUrl)
                .post(jsonBody.toRequestBody("application/json".toMediaType()))
                .build()

            reissueClient.newCall(reissueRequest).execute()
        } catch (_: Exception) {
            null
        }
    }

    private fun parseAndSaveTokens(response: Response): Boolean {
        return try {
            val jsonResponse = JSONObject(response.body.string())
            if (jsonResponse.optBoolean("success", false)) {
                val data = jsonResponse.getJSONObject("data")
                val newAccessToken = data.getString("accessToken")
                val newRefreshToken = data.getString("refreshToken")

                runBlocking {
                    tokenDataStore.saveTokens(
                        accessToken = newAccessToken,
                        refreshToken = newRefreshToken
                    )
                }
                true
            } else {
                false
            }
        } catch (_: Exception) {
            false
        }
    }
}