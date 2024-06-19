package com.WalkiePaw.domain.entity.notification;

import com.WalkiePaw.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Integer id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime notificationTime;
    private boolean isRead;

    /**
     * isRead 파라미터로 사용할지,
     * 사용한다면 false로 하드코딩할지 고민하기
     */
    private Notification(Member member, LocalDateTime notificationTime) {
        this.member = member;
        this.notificationTime = notificationTime;
    }

    /**
     * Notification 생성 메서드
     */
    public Notification createNotification (Member member, LocalDateTime notificationTime) {
        return new Notification(member, notificationTime);
    }
}
