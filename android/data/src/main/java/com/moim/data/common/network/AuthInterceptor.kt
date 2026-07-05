package com.moim.data.common.network

import com.moim.data.common.network.AuthorizationFilterUrl.excludePaths
import com.moim.data.common.source.TokenDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private object AuthorizationFilterUrl {
    val excludePaths = listOf("/login", "/reissue", "/logout")
}

class AuthInterceptor @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (excludePaths.any { originalRequest.url.encodedPath.contains(it) }) {
            return chain.proceed(originalRequest)
        }

        val accessToken = tokenDataStore.accessTokenFlow

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}