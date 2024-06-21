package com.WalkiePaw.presentation.domain.qna;

import com.WalkiePaw.domain.qna.entity.QnaStatus;
import com.WalkiePaw.presentation.domain.member.MemberRequest;
import lombok.Getter;

@Getter
public class QnaRequest {
    private MemberRequest member;
    private String title;
    private String content;
    private String reply;
    private QnaStatus status;

    /**
     * QnaRequest를 jpa entity로 변환시켜주는 메서드?
     */
}
