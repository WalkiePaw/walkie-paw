package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import static com.WalkiePaw.domain.board.entity.QBoard.board;
import static com.WalkiePaw.domain.chatroom.entity.QChatroom.*;
import static com.WalkiePaw.domain.member.entity.QMember.member;
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
                page -> page.select(Projections.bean(TransactionResponse.class,
                                chatroom.id.as("chatroomId"),
                                board.title.as("title"),
                                board.category.as("category"),
                                chatroom.completedDate.as("createdDate"),
                                chatroom.member.nickname.as("memberNickName"),
                                Expressions.as(
                                        JPAExpressions.select(review.id.count().gt(0))
                                                .from(review)
                                                .where(review.reviewer.id.eq(memberId)
                                                        .and(review.chatroom.id.eq(chatroom.id))), "hasReview"
                                )))
                        .from(chatroom)
                        .join(chatroom.member, member)
                        .join(chatroom.board, board)
                        .where(chatroom.status.eq(ChatroomStatus.COMPLETED)
                                .and(chatroom.board.member.id.eq(memberId).or(chatroom.member.id.eq(memberId)))));

    }
}
