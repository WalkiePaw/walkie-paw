package com.WalkiePaw.presentation.domain.qna.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import lombok.Getter;

@Getter
public class QnaAddRequest {
    private Integer memberId;
    private String title;
    private String content;

    public Qna toEntity(Member member) {
        return Qna.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
