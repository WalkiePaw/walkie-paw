package com.WalkiePaw.domain.entity.chat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatPhoto {
    @Id
    @GeneratedValue
    @Column(name = "chat_photo_id")
    private Integer id;
    private String oriName;
    private String uuidName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_msg_id")
    private ChatMessage chatMessage;
}
