package com.moim.backend.core.error

class ErrorException(
    val errorCode: String,
    override val message: String
) : RuntimeException(message)