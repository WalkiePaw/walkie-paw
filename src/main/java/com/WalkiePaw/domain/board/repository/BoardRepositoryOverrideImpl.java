package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardCategory;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.WalkiePaw.domain.board.entity.QBoard.*;
import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryOverrideImpl implements BoardRepositoryOverride {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllNotDeleted(final BoardCategory category) {
        return jpaQueryFactory
                .selectFrom(board)
                .join(board.member).fetchJoin()
                .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                .fetch();
    }

    @Override
    public List<Board> findBySearchCond(final String title, final String content) {
        return jpaQueryFactory
                .selectFrom(board)
                .join(board.member).fetchJoin()
                .where(titleCond(title), contentCond(content))
                .fetch();
    }

    private BooleanExpression contentCond(final String content) {
        return hasText(content) ? board.content.like("%" + content + "%") : null;
    }

    private BooleanExpression titleCond(final String title) {
        return hasText(title) ? board.title.like("%" + title + "%") : null;
    }

    @Override
    public List<BoardMypageListResponse> findMyBoardsBy(final Integer memberId, final BoardCategory category) {
        return jpaQueryFactory
                .select(Projections.fields(BoardMypageListResponse.class,
                        board.id.as("boardId"),
                        board.title,
                        board.content,
                        board.createdDate
                )).from(board).where(board.member.id.eq(memberId).and(board.category.eq(category)))
                .fetch();
    }

}
