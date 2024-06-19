package com.WalkiePaw.domain.entity.qna;

import com.WalkiePaw.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Qna {
    @Id
    @GeneratedValue
    @Column(name = "qna_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private LocalDate createdDate;
    private String reply;
    @Enumerated(EnumType.STRING)
    private QnaStatus status;

    /**
     *
     */
    private Qna(Member member, String title, String content, LocalDate createdDate) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.status = QnaStatus.WAITING;
    }

    /**
     * QnA 생성 메서드
     */
    public Qna createQnA(Member member, String title, String content, LocalDate createdDate) {
        return new Qna(member, title, content, createdDate);
    }
}
