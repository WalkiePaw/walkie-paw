package com.WalkiePaw.presentation.domain.qna.dto;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import com.WalkiePaw.presentation.domain.member.dto.MemberRequest;
import com.WalkiePaw.presentation.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QnaResponse {

    private MemberResponse member;
    private String title;
    private String content;
    private String reply;
    private QnaStatus status;

    /**
     * 엔티티를 dto로 변환하는 메서드
     */
    public static QnaResponse from(Qna qna) {
        return new QnaResponse(MemberResponse.from(qna.getMember()), qna.getTitle(), qna.getContent(), qna.getReply(), qna.getStatus());
    }
}
