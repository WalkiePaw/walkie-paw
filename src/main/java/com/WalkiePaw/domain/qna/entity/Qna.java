package com.WalkiePaw.domain.qna.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Qna extends BaseEntity {
    @Id
    @GeneratedValue
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

    /**
     *
     */
    public Qna(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.status = QnaStatus.WAITING;
    }

//    /**
//     * QnA 생성 메서드
//     */
//    public Qna createQnA(Member member, String title, String content, LocalDate createdDate) {
//        return new Qna(member, title, content, createdDate);
//    }
}
