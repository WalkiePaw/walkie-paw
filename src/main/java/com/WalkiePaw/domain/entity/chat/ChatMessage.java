package com.WalkiePaw.domain.entity.chat;

import com.WalkiePaw.domain.entity.BaseEntity;
import com.WalkiePaw.domain.entity.chatroom.Chatroom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chat_msg_id")
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;
    private boolean isRead;
    @Column(name = "msg_content")
    private String content;
    @Enumerated(EnumType.STRING)
    private ChatMessageStatus status;

}
