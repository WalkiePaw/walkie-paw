package com.WalkiePaw.presentation.domain.qna.dto;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import com.WalkiePaw.presentation.domain.member.dto.MemberRequest;
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
    public Qna toEntity() {
        return Qna.builder()
                .member(this.member.toEntity())
                .title(this.title)
                .content(this.content)
                .build();
    }
}
