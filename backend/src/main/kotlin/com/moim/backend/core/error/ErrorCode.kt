package com.moim.backend.core.error

enum class ErrorCode(val message: String) {
    INVALID_GOOGLE_TOKEN("유효하지 않거나 위조된 구글 토큰입니다."),
    EXPIRED_TOKEN("Access Token이 만료되었거나 유효하지 않습니다."),
    USER_NOT_FOUND("유저 정보를 찾을 수 없습니다."),
    SECURITY_BREACH("비정상적인 접근이 감지되어 보안을 위해 강제 로그아웃 처리되었습니다.")
}