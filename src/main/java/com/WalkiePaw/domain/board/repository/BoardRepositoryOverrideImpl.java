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

    /**
     * TODO
     * - board photo 가져오는 쿼리 갯수만큼 나가는 문제 나중에 처리하기
     */
    @Override
    public Slice<BoardListResponse> findAllNotDeleted(final BoardCategory category, Pageable pageable) {
        return slice(pageable,
                slice -> slice.selectFrom(board)
                        .join(board.member).fetchJoin()
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                        .orderBy(board.createdDate.desc()),
                BoardListResponse::from
        );
    }


    @Override
    public Slice<BoardListResponse> findBySearchCond(final String title, final String content, final BoardCategory category, Pageable pageable) {
        return slice(pageable, slice -> slice.selectFrom(board)
                        .join(board.member).fetchJoin()
                        .where(
                                titleCond(title),
                                contentCond(content),
                                categoryCond(category))
                        .orderBy(board.createdDate.desc()),
                BoardListResponse::from);
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
                        .join(boardLike.board, board)
                        .join(boardLike.member, member)
                        .where(member.id.eq(memberId).and(board.status.ne(BoardStatus.DELETED)))
                        .orderBy(board.createdDate.desc()),
                BoardListResponse::from
        );
    }

    public Optional<Board> findWithPhotoBy(Integer boardId) {
        return Optional.ofNullable(selectFrom(board)
                .leftJoin(board.photos)
                .where(board.id.eq(boardId)).fetchOne());
    }
}
