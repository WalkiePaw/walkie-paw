package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.*;
import com.WalkiePaw.domain.member.entity.QMember;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.WalkiePaw.presentation.domain.board.dto.BoardListResponse;
import com.WalkiePaw.presentation.domain.board.dto.BoardMypageListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.WalkiePaw.domain.board.entity.QBoard.board;
import static com.WalkiePaw.domain.board.entity.QBoardLike.boardLike;
import static com.WalkiePaw.domain.board.entity.QBoardPhoto.*;
import static com.WalkiePaw.domain.member.entity.QMember.*;
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
                                getBoardPhoto(),
                                board.member.photo.as("memberPhoto"),
                                boardLikeQuery(memberId)
                        ))
                        .from(board)
                        .join(board.member)
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)))
                        .orderBy(board.createdDate.desc()));
    }

    @Override
    public Slice<BoardListResponse> findAllNotDeleted(final Integer memberId, final BoardCategory category, final String dong, Pageable pageable) {
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
                                getBoardPhoto(),
                                board.member.photo.as("memberPhoto"),
                                boardLikeQuery(memberId)
                        ))
                        .from(board)
                        .join(board.member)
                        .where(board.status.ne(BoardStatus.DELETED).and(board.category.eq(category)),
                                dongCond(dong))
                        .orderBy(board.createdDate.desc()));
    }

    private static JPQLQuery<String> getBoardPhoto() {
        return JPAExpressions
                .select(boardPhoto.url.min().as("photoUrls"))
                .from(boardPhoto)
                .where(boardPhoto.board.id.eq(board.id));
    }

    private static BooleanExpression boardLikeQuery(Integer memberId) {
        return JPAExpressions.selectOne()
                .from(boardLike)
                .where(boardLike.board.eq(board)
                        .and(boardLike.member.id.eq(memberId)))
                .exists();
    }


    private static BooleanExpression isLikedCond(final Integer memberId) {
        return boardLike.board.id.eq(board.id).and(boardLike.member.id.eq(memberId));
    }


    @Override
    public Slice<BoardListResponse> findBySearchCond(final Integer memberId, final String title, final String content, final BoardCategory category, Pageable pageable) {
        return slice(pageable, slice -> slice
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
                        getBoardPhoto(),
                        board.member.photo.as("memberPhoto"),
                        boardLikeQuery(memberId)
                ))
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

    private BooleanExpression dongCond(final String address) {
      return  hasText(address) ? board.member.memberAddress.like("%" + address + "%") : null;
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
                        getBoardPhoto(),
                        board.member.photo.as("memberPhoto"),
                        boardLikeQuery(memberId)
                ))
                .from(boardLike)
                .leftJoin(boardLike.board, board)
                .leftJoin(boardLike.member, member)
                .where(boardLike.member.id.eq(memberId).and(boardLike.board.status.ne(BoardStatus.DELETED)))
                .orderBy(board.createdDate.desc()));
    }

    public Optional<Board> findWithPhotoBy(Integer boardId) {
        return Optional.ofNullable(selectFrom(board)
                .leftJoin(board.photos)
                .where(board.id.eq(boardId)).fetchOne());
    }
}
