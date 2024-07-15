package com.WalkiePaw.domain.qna.repository;

import com.WalkiePaw.domain.qna.entity.Qna;
import com.WalkiePaw.domain.qna.entity.QnaStatus;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.WalkiePaw.domain.qna.entity.QQna.qna;
import static org.springframework.util.StringUtils.hasText;

public class QnaRepositoryOverrideImpl extends Querydsl4RepositorySupport implements QnaRepositoryOverride {

    public QnaRepositoryOverrideImpl() {
        super(Qna.class);
    }

    @Override
    public List<Qna> findAllByCond(final String status) {
        return selectFrom(qna)
                .where(statusCond(status))
                .fetch();
    }

    @Override
    public List<Qna> findByMemberId(final Integer memberId) {
        return selectFrom(qna)
                .where(qna.member.id.eq(memberId))
                .fetch();
    }

    private BooleanExpression statusCond(String status) {
        if (hasText(status)) {
            if (status.equals("RESOLVED")) {
                return qna.status.eq(QnaStatus.COMPLETED);
            } else if (status.equals("UNRESOLVED")) {
                return qna.status.eq(QnaStatus.WAITING);
            }
        }
        return null;
    }
}
