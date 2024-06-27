package com.WalkiePaw.presentation.domain.qna.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import lombok.Getter;

@Getter
public class QnaUpdateRequest {
    private Integer memberId;
    private String title;
    private String content;
    private String reply;
    private QnaStatus status;
}
