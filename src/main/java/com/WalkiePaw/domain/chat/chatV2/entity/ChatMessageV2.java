package com.WalkiePaw.domain.chat.chatV2.entity;

import com.WalkiePaw.domain.common.BaseEntity;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageV2 extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "chat_msg_id_v2")
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver;

    private boolean isRead;

    @Column(name = "msg_content")
    private String content;

    public ChatMessageV2(Chatroom chatroom, Member receiver, String content) {
        this.chatroom = chatroom;
        this.receiver = receiver;
        this.content = content;
    }

//    /**
//     * ChatMessage 생성 메서드
//     * @param receiver 받을 member를 가르킴
//     */
//    public ChatMessage createChatMessage(Chatroom chatroom, Member receiver, String content) {
//        return new ChatMessage(chatroom, receiver, content);
//    }
}
