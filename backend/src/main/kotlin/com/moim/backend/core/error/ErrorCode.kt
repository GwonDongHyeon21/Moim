package com.moim.backend.core.error

enum class ErrorCode(val message: String) {
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    INVALID_GOOGLE_TOKEN("유효하지 않거나 위조된 구글 토큰입니다."),
    EXPIRED_TOKEN("Token이 만료되었거나 유효하지 않습니다."),
    SECURITY_BREACH("비정상적인 접근이 감지되어 보안을 위해 강제 로그아웃 처리되었습니다."),

    USER_NOT_FOUND("유저 정보를 찾을 수 없습니다."),

    ROOM_NOT_FOUND("방 정보를 찾을 수 없습니다."),
    ALREADY_JOINED_ROOM("이미 참여한 방입니다."),
    FULL_ROOM("방 정원이 가득 찼습니다."),
    MAX_ROOM_LIMIT("생성 가능한 방 개수가 최대치라 방 생성이 불가능 합니다."),
    ROOM_DEADLINE_EXPIRED("해당 방의 모집 마감 시간이 지났습니다."),
    NOT_ROOM_PERMISSION("방장만 수정할 수 있습니다."),

    CANDIDATE_NOT_FOUND("후보지를 찾을 수 없습니다."),
    VOTE_RESULTS_BLINDED("마감 전에는 투표 결과를 볼 수 없습니다."),
    CANDIDATE_COUNT_LIMIT("각 카테고리별로 최대 3개까지만 후보를 등록할 수 있습니다.")
}