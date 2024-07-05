package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.WalkiePaw.domain.chatroom.entity.QChatroom.*;

@Repository
@RequiredArgsConstructor
public class ChatroomRepositoryOverrideImpl implements ChatroomRepositoryOverride {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatroomListResponse> findByMemberId(final Integer memberId) {
        return jpaQueryFactory
                .select(Projections.fields(ChatroomListResponse.class,
                        chatroom.id, chatroom.board.location, chatroom.member.nickname
                        , chatroom.latestMessage, chatroom.modifiedDate, chatroom.unreadCount
                ))
                .from(chatroom)
                .where(chatroom.board.id.eq(memberId).or(chatroom.member.id.eq(memberId)))
                .fetch();
    }
}
