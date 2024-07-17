package com.WalkiePaw.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_REQUEST(1000, "유효한 요청이 아닙니다."),

    NOT_FOUND_BOARD_ID(1001, "요청한 ID의 게시글이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_ID(1002, "요청한 ID의 회원이 존재하지 않습니다."),
    NOT_FOUND_CHATROOM_ID(1003, "요청한 ID의 채팅방이 존재하지 않습니다."),
    NOT_FOUND_REVIEW_ID(1004, "요청한 ID의 리뷰가 존재하지 않습니다."),

    NOT_FOUND_EMAIL(1005, "요청한 EMAIL의 회원이 존재하지 않습니다."),
    DUPLICATED_EMAIL(1006, "요청한 EMAIL의 회원이 이미 존재합니다."),

    NOT_FOUND_NICKNAME(1007, "요청한 NICKNAME의 회원이 존재하지 않습니다."),

    NOT_FOUND_QNA_ID(1008, "요쳥한 ID의 문의가 존재하지 않습니다."),

    NOT_FOUND_MEMBER(2001, "회원을 찾을 수 없습니다."),

    MAIL_SEND_FAIL(5001, "메일 전송에 실패하였습니다.");


    private final int code;
    private final String message;
}
