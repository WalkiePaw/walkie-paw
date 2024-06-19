package com.WalkiePaw.domain.entity.notification;

import com.WalkiePaw.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
