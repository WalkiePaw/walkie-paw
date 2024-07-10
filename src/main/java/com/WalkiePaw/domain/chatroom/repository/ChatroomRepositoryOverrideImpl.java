package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import static com.WalkiePaw.domain.chatroom.entity.QChatroom.*;
import static com.WalkiePaw.domain.review.entity.QReview.*;

@Repository
public class ChatroomRepositoryOverrideImpl extends Querydsl4RepositorySupport implements ChatroomRepositoryOverride {

    public ChatroomRepositoryOverrideImpl() {
        super(Chatroom.class);
    }

    @Override
    public Slice<ChatroomListResponse> findByMemberId(final Integer memberId, Pageable pageable) {
        return slice(pageable,
                query -> query.select(
                        Projections.constructor(ChatroomListResponse.class,
                                chatroom.id, chatroom.board.location, chatroom.member.nickname
                                , chatroom.latestMessage, chatroom.modifiedDate, chatroom.unreadCount
                        ))
                        .from(chatroom)
                        .where(chatroom.board.member.id.eq(memberId).or(chatroom.member.id.eq(memberId))));
    }

    @Override
    public Page<TransactionResponse> findTransaction(final Integer memberId, Pageable pageable) {
        return page(pageable,
                page -> page.select(Projections.constructor(TransactionResponse.class,
                                chatroom.id,
                                chatroom.board.title,
                                chatroom.member.nickname.as("memberNickName"),
                                chatroom.completedDate,
                                new CaseBuilder()
                                        .when(review.id.isNotNull()).then(true)
                                        .otherwise(false).as("hasReview")
                        ))
                        .from(chatroom)
                        .leftJoin(review).on(chatroom.id.eq(review.chatroom.id))
                        .where(chatroom.board.member.id.eq(memberId).or(chatroom.member.id.eq(memberId))));
    }
}
