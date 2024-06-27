package com.WalkiePaw.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_photo_id_v2")
    private Integer id;
    private String oriName;
    private String uuidName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_msg_id")
    private ChatMessage chatMessage;

    public ChatPhoto(String oriName, String uuidName, ChatMessage chatMessage) {
        this.oriName = oriName;
        this.uuidName = uuidName;
        this.chatMessage = chatMessage;
    }

//    public ChatPhoto createChatPhoto(String oriName, String uuidName, ChatMessage chatMessage) {
//        return new ChatPhoto(oriName, uuidName, chatMessage);
//    }
}
