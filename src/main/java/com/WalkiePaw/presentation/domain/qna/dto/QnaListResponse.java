package com.WalkiePaw.presentation.domain.qna.dto;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QnaListResponse {

    private Integer qnaId;
    private Integer memberId;
    private String writerName;
    private String title;
    private QnaStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    /**
     * 엔티티를 dto로 변환하는 메서드
     */
    public static QnaListResponse from(Qna qna) {
        return new QnaListResponse(
                qna.getId(),
                qna.getMember().getId(),
                qna.getMember().getName(),
                qna.getTitle(),
                qna.getStatus(),
                qna.getCreatedDate(),
                qna.getModifiedDate()
                );
    }
}
