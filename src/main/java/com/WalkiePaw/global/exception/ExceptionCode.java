package com.WalkiePaw.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOARD_ID(1001, "요청한 ID의 게시글이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_ID(1002, "요청한 ID의 회원이 존재하지 않습니다."),
    NOT_FOUND_CHATROOM_ID(1003, "요청한 ID의 채팅방이 존재하지 않습니다."),
    NOT_FOUND_REVIEW_ID(1004, "요청한 ID의 리뷰가 존재하지 않습니다."),

    NOT_FOUND_EMAIL(1005, "요청한 EMAIL의 사용자가 존재하지 않습니다."),
    DUPLICATED_EMAIL(1006, "요청한 EMAIL의 사용자가 이미 존재합니다.");


    private final int code;
    private final String message;
}
