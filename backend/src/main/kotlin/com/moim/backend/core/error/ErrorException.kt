package com.moim.backend.core.error

import org.springframework.http.HttpStatus

class ErrorException(
    val httpStatus: HttpStatus,
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)