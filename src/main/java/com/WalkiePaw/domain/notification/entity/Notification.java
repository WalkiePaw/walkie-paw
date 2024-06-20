package com.WalkiePaw.domain.notification.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Integer id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private boolean isRead;

    /**
     * isRead 파라미터로 사용할지,
     * 사용한다면 false로 하드코딩할지 고민하기
     */
    public Notification(Member member) {
        this.member = member;
    }

//    /**
//     * Notification 생성 메서드
//     */
//    public Notification createNotification (Member member, LocalDateTime notificationTime) {
//        return new Notification(member, notificationTime);
//    }
}
