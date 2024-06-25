package com.WalkiePaw.domain.chat.chatV2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatPhotoV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_photo_id_v2")
    private Integer id;
    private String oriName;
    private String uuidName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_msg_id")
    private ChatMessageV2 chatMessage;

    public ChatPhotoV2(String oriName, String uuidName, ChatMessageV2 chatMessage) {
        this.oriName = oriName;
        this.uuidName = uuidName;
        this.chatMessage = chatMessage;
    }

//    public ChatPhoto createChatPhoto(String oriName, String uuidName, ChatMessage chatMessage) {
//        return new ChatPhoto(oriName, uuidName, chatMessage);
//    }
}
