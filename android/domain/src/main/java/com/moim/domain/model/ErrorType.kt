package com.moim.domain.model

sealed class ErrorType(message: String) : Exception(message) {
    object TokenNotFound : ErrorType("리프레시 토큰이 존재하지 않습니다.") {
        private fun readResolve(): Any = TokenNotFound
    }

    object Unknown : ErrorType("알 수 없는 인증 오류가 발생했습니다.") {
        private fun readResolve(): Any = Unknown
    }
}