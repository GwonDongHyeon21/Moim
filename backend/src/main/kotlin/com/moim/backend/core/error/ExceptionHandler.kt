package com.moim.backend.core.error

import com.moim.backend.core.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ErrorException::class)
    fun handleErrorException(e: ErrorException): ResponseEntity<ApiResponse<Nothing>> {

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(e.errorCode, e.message))
    }
}