package com.moim.backend.core.response

class ApiResponse<T> private constructor(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(true, data, null)

        fun fail(code: String, message: String): ApiResponse<Nothing> =
            ApiResponse(false, null, ErrorResponse(code, message))
    }
}