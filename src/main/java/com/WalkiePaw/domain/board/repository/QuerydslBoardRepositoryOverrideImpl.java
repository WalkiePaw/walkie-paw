package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.WalkiePaw.domain.board.entity.QBoard.*;

@Repository
@RequiredArgsConstructor
public class QuerydslBoardRepositoryOverrideImpl implements BoardRepositoryOverride {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllNotDeleted() {
        return jpaQueryFactory
                .selectFrom(board)
                .join(board.member).fetchJoin()
                .where(board.status.ne(BoardStatus.DELETED))
                .fetch();
    }

}
