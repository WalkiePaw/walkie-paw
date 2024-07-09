package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.*;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.WalkiePaw.domain.board.entity.QBoard.*;
import static com.WalkiePaw.domain.board.entity.QBoardLike.*;
import static com.WalkiePaw.domain.member.entity.QMember.member;
import static org.springframework.util.StringUtils.*;

@Repository
public class BoardRepositoryOverrideImpl extends Querydsl4RepositorySupport implements BoardRepositoryOverride {

    public BoardRepositoryOverrideImpl() {
        super(Board.class);
    }

    @Override
    public List<Board> findAllNotDeleted(final BoardCategory category) {
        return selectFrom(board)
                .join(board.member).fetchJoin()
                .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                .fetch();
    }

    @Override
    public List<Board> findBySearchCond(final String title, final String content) {
        return selectFrom(board)
                .join(board.member).fetchJoin()
                .where(
                        titleCond(title),
                        contentCond(content),
                        categoryCond(category))
                .fetch();
    }

    private BooleanExpression categoryCond(final BoardCategory category) {
        return category != null ? board.category.eq(category) : null;
    }

    private BooleanExpression contentCond(final String content) {
        return hasText(content) ? board.content.like("%" + content + "%") : null;
    }

    private BooleanExpression titleCond(final String title) {
        return hasText(title) ? board.title.like("%" + title + "%") : null;
    }

    @Override
    public List<BoardMypageListResponse> findMyBoardsBy(final Integer memberId, final BoardCategory category) {
        return select(Projections.fields(BoardMypageListResponse.class,
                        board.id.as("boardId"),
                        board.title,
                        board.content,
                        board.createdDate
                )).from(board).where(board.member.id.eq(memberId).and(board.category.eq(category)))
                .fetch();
    }

    @Override
    public Slice<BoardListResponse> findLikeBoardList(final Integer memberId, final Pageable pageable) {
//        List<Board> result = jpaQueryFactory
//                .select(board)
//                .from(boardLike)
//                .join(boardLike.board, board)
//                .join(boardLike.member, member)
//                .where(member.id.eq(memberId).and(board.status.ne(BoardStatus.DELETED)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)
//                .fetch();
//        List<BoardListResponse> boardListResponses = result.stream().map(BoardListResponse::from).toList();
//        boolean hasNext = true;
//        if (result.size() > pageable.getPageSize()) {
//            hasNext = false;
//            result.remove(pageable.getPageSize());
//        }

        return slice(pageable, slice -> slice
                .select(board)
                .from(boardLike)
                .join(boardLike.board, board)
                .join(boardLike.member, member)
                .where(member.id.eq(memberId).and(board.status.ne(BoardStatus.DELETED))));
    }

}
