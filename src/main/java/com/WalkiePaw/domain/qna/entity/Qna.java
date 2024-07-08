package com.WalkiePaw.domain.qna.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.qna.dto.QnaUpdateRequest;
import com.WalkiePaw.presentation.domain.qna.dto.replyUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Qna extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private String reply;
    @Enumerated(EnumType.STRING)
    private QnaStatus status;

    @Builder
    public Qna(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = QnaStatus.WAITING;
    }

    public void update(QnaUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.reply = request.getReply();
        this.status = request.getStatus();
    }

    public void updateReply(final replyUpdateRequest request) {
        this.reply = request.getReply();
        this.status = QnaStatus.COMPLETED;
    }

//    /**
//     * QnA 생성 메서드
//     */
//    public Qna createQnA(Member member, String title, String content, LocalDate createdDate) {
//        return new Qna(member, title, content, createdDate);
//    }
}
