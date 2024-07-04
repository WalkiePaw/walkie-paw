package com.WalkiePaw.domain.board.repository;

import com.WalkiePaw.domain.board.entity.Board;
import com.WalkiePaw.domain.board.entity.BoardStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.WalkiePaw.domain.board.entity.QBoard.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryOverrideImpl implements BoardRepositoryOverride {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> findAllNotDeleted() {
        return jpaQueryFactory
                .selectFrom(board)
                .join(board.member).fetchJoin()
                .where(board.status.ne(BoardStatus.DELETED))
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

    private Predicate contentCond(final String content) {
        return content != null ? board.content.like("%" + content + "%") : null;
    }

    private Predicate titleCond(final String title) {
        return title != null ? board.title.like("%" + title + "%") : null;
    }
}
