package com.moim.data.common.network

import com.moim.data.common.model.ApiResponse

suspend fun <T> apiCall(call: suspend () -> ApiResponse<T>): Result<T> {
    return try {
        val response = call()

        if (response.success && response.data != null) {
            Result.success(value = response.data)
        } else {
            throw Exception(response.error?.message ?: "Unknown Server Error")
        }
    } catch (e: Exception) {
        Result.failure(exception = e)
    }
}