package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.*;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
    public Slice<BoardListResponse> findAllNotDeleted(final BoardCategory category, Pageable pageable) {
        return sliceResponse(pageable,
                slice -> slice.selectFrom(board)
                        .join(board.member).fetchJoin()
                        .leftJoin(board.photos).fetchJoin()
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                        .groupBy(board)
                        .orderBy(board.createdDate.desc()));
    }


    @Override
    public Slice<BoardListResponse> findBySearchCond(final String title, final String content, final BoardCategory category, Pageable pageable) {
        return sliceResponse(pageable, slice -> slice.selectFrom(board)
                .join(board.member).fetchJoin()
                .leftJoin(board.photos).fetchJoin()
                .where(
                        titleCond(title),
                        contentCond(content),
                        categoryCond(category))
                .groupBy(board)
                .orderBy(board.createdDate.desc()));
    }

    /**
     * Support의 page method custom
     */
    private Slice<BoardListResponse> sliceResponse(Pageable pageable, Function<JPAQueryFactory, JPAQuery> sliceQuery) {
        JPAQuery query = (JPAQuery) sliceQuery.apply(getJpaQueryFactory())
                .offset(pageable.getOffset()).limit(pageable.getPageSize());
        List<Board> content = createContent(pageable, query);
        boolean hasNext = isHasNext(pageable, content);
        List<BoardListResponse> boards = content.stream().map(BoardListResponse::from).toList();
        return new SliceImpl<>(boards, pageable, hasNext);
    }

    private static int sliceSize(Pageable pageable) {
        return pageable.getPageSize() + 1;
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
        return sliceResponse(pageable, slice -> slice
                .select(board)
                .from(boardLike)
                .join(boardLike.board, board)
                .join(boardLike.member, member)
                .leftJoin(board.photos).fetchJoin()
                .where(member.id.eq(memberId).and(board.status.ne(BoardStatus.DELETED)))
                .groupBy(board)
                .orderBy(board.createdDate.desc()));
    }

    public Optional<Board> findWithPhotoBy(Integer boardId) {
        return Optional.ofNullable(selectFrom(board)
                .leftJoin(board.photos)
                .where(board.id.eq(boardId)).fetchOne());
    }
}
