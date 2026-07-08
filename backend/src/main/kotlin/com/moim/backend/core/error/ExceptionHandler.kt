package com.moim.backend.core.error

import com.moim.backend.core.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ErrorException::class)
    fun handleErrorException(e: ErrorException): ResponseEntity<ApiResponse<Nothing>> {

        return ResponseEntity
            .status(e.httpStatus)
            .body(ApiResponse.fail(e.errorCode.toString(), e.errorCode.message))
    }
}