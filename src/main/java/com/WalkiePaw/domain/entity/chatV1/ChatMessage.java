package com.WalkiePaw.domain.entity.chatV1;

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
    private boolean isFromBoardWriter;

    /**
     * isRead와 isFromBoardWriter를 파라미터로 사용할지,
     * 사용한다면 flase로 하드코딩할 지 고민하기
     */
    private ChatMessage(Chatroom chatroom, String content) {
        this.chatroom = chatroom;
        this.content = content;
    }

    /**
     * chatMessage 생성 메서드
     */
    public ChatMessage createChatMessage(Chatroom chatroom, String content) {
        return new ChatMessage(chatroom, content);
    }
}
