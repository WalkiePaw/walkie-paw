package com.WalkiePaw.domain.chatroom.repository;

import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.domain.chatroom.entity.Chatroom;
import com.WalkiePaw.domain.chatroom.entity.ChatroomStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.chatroom.dto.ChatroomListResponse;
import com.WalkiePaw.presentation.domain.chatroom.dto.TransactionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.WalkiePaw.domain.board.entity.QBoard.board;
import static com.WalkiePaw.domain.chatroom.entity.ChatroomStatus.COMPLETED;
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
                                        chatroom.id, chatroom.board.location,
                                        Expressions.stringTemplate("CASE WHEN {0} = {1} THEN {2} ELSE {3} END",
                                                memberId, chatroom.member.id,
                                                chatroom.board.member.nickname, chatroom.member.nickname).as("nickname"),
                                        chatroom.latestMessage, chatroom.modifiedDate, chatroom.unreadCount,
                                        chatroom.board.title.as("boardTitle"),
                                        Expressions.stringTemplate("CASE WHEN {0} = {1} THEN {2} ELSE {3} END",
                                                memberId, chatroom.member.id,
                                                chatroom.board.member.photo, chatroom.member.photo).as("memberPhoto"),
                                        Expressions.asBoolean(chatroom.status.eq(COMPLETED))
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
                                Expressions.stringTemplate("CASE WHEN {0} = {1} THEN {2} ELSE {3} END",
                                        memberId, chatroom.member.id,
                                        chatroom.board.member.nickname, chatroom.member.nickname).as("memberNickName"),
                                chatroom.completedDate.as("createdDate"),
                                Expressions.booleanTemplate("CASE WHEN {0} = {1} THEN {2} ELSE {3} END",
                                        memberId, chatroom.member.id,
                                        JPAExpressions.selectOne().from(review)
                                                .where(review.reviewer.id.eq(chatroom.member.id)
                                                        .and(review.chatroom.id.eq(chatroom.id))).exists(),
                                        JPAExpressions.selectOne().from(review)
                                                .where(review.reviewer.id.eq(chatroom.board.member.id)
                                                        .and(review.chatroom.id.eq(chatroom.id))).exists()).as("hasReview"),
                                board.category.as("category")
                        ))
                        .from(chatroom)
                        .join(chatroom.member, member)
                        .join(chatroom.board, board)
                        .where(chatroom.status.eq(COMPLETED)
                                .and(chatroom.board.member.id.eq(memberId).or(chatroom.member.id.eq(memberId)))));
    }

    @Override
    public Optional<Chatroom> findByMemberIdAndBoardId(final Integer memberId, final Integer boardId) {
        return Optional.ofNullable(selectFrom(chatroom)
                .where(chatroom.board.id.eq(boardId).and((chatroom.member.id.eq(memberId))))
                .fetchFirst());
    }

    @Override
    public Optional<Chatroom> findByWriterIdAndBoardId(final Integer writerId, final Integer boardId) {
        return Optional.ofNullable(selectFrom(chatroom)
                .where(chatroom.board.id.eq(boardId).and((chatroom.board.member.id.eq(writerId))))
                .fetchFirst());
    }

}
