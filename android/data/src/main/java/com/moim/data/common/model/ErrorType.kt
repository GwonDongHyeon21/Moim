package com.moim.data.common.model

sealed class ErrorType(message: String) : Exception(message) {

    class TokenNotFound : ErrorType("리프레시 토큰이 존재하지 않습니다.")

    class Unknown : ErrorType("알 수 없는 인증 오류가 발생했습니다.")
}