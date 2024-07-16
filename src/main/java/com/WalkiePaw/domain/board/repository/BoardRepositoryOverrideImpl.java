package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.*;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.WalkiePaw.domain.board.entity.QBoard.board;
import static com.WalkiePaw.domain.board.entity.QBoardLike.boardLike;
import static com.WalkiePaw.domain.board.entity.QBoardPhoto.*;
import static com.WalkiePaw.domain.member.entity.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class BoardRepositoryOverrideImpl extends Querydsl4RepositorySupport implements BoardRepositoryOverride {

    public BoardRepositoryOverrideImpl() {
        super(Board.class);
    }

    @Override
    public Slice<BoardListResponse> findAllNotDeleted(final BoardCategory category, Pageable pageable) {
        return slice(pageable,
                slice -> slice
                        .select(board)
                        .from(board)
                        .join(board.member).fetchJoin()
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                        .orderBy(board.createdDate.desc()),
                b -> BoardListResponse.from(b, false)
        );
    }
    @Override
    public Slice<BoardListResponse> findAllNotDeleted(final Integer memberId, final BoardCategory category, Pageable pageable) {
        return slice(pageable,
                slice -> slice
                        .select(Projections.constructor(BoardListResponse.class,
                                board.id,
                                board.title,
                                board.content,
                                board.location,
                                board.price,
                                board.priceType,
                                board.endTime,
                                board.startTime,
                                board.likeCount,
                                board.member.nickname.as("memberNickName"),
                                board.status,
                                board.category,
                                board.priceProposal,
                                board.member.photo.as("memberPhoto"),
                                JPAExpressions.selectOne()
                                        .from(boardLike)
                                        .where(boardLike.board.eq(board)
                                                .and(boardLike.member.id.eq(memberId)))
                                        .exists()
                        ))
                        .from(board)
                        .join(board.member)
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                        .orderBy(board.createdDate.desc()));
    }


    private static BooleanExpression isLikedCond(final Integer memberId) {
        return boardLike.board.id.eq(board.id).and(boardLike.member.id.eq(memberId));
    }


    @Override
    public Slice<BoardListResponse> findBySearchCond(final String title, final String content, final BoardCategory category, Pageable pageable) {
        return slice(pageable, slice -> slice
                .select(board
                )
                .from(board)
                .join(board.member).fetchJoin()
                .where(
                        titleCond(title),
                        contentCond(content),
                        categoryCond(category))
                .orderBy(board.createdDate.desc()));
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
    public Page<BoardMypageListResponse> findMyBoardsBy(final Integer memberId, final BoardCategory category, Pageable pageable) {
        return page(pageable, page -> page.select(Projections.fields(BoardMypageListResponse.class,
                        board.id.as("boardId"),
                        board.title,
                        board.content,
                        board.createdDate
                )).from(board).where(board.member.id.eq(memberId).and(board.category.eq(category)))
                .orderBy(board.createdDate.desc()));
    }

    @Override
    public Slice<BoardListResponse> findLikeBoardList(final Integer memberId, final Pageable pageable) {
        return slice(pageable, slice -> slice
                        .select(board)
                        .from(boardLike)
                        .leftJoin(boardLike.board)
                        .leftJoin(boardLike.member)
                        .where(boardLike.member.id.eq(memberId).and(boardLike.board.status.ne(BoardStatus.DELETED)))
                        .orderBy(board.createdDate.desc()),
                b -> BoardListResponse.from(b, true)
        );
    }

    public Optional<Board> findWithPhotoBy(Integer boardId) {
        return Optional.ofNullable(selectFrom(board)
                .leftJoin(board.photos)
                .where(board.id.eq(boardId)).fetchOne());
    }
}
