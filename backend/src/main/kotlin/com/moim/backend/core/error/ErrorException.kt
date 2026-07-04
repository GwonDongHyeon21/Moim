package com.moim.backend.core.error

import org.springframework.http.HttpStatus

class ErrorException(
    val httpStatus: HttpStatus,
    val errorCode: String,
    override val message: String
) : RuntimeException(message)