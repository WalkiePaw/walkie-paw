package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.global.util.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static com.WalkiePaw.domain.member.entity.QMember.member;
import static org.springframework.util.StringUtils.*;

public class MemberRepositoryOverrideImpl extends Querydsl4RepositorySupport implements MemberRepositoryOverride {

    public MemberRepositoryOverrideImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> findBySearchCond(final String name, final String nickname, final String email, final Integer reportedCnt) {
        return selectFrom(member)
                .where(
                        nameCond(name),
                        nicknameCond(nickname),
                        emailCond(email),
                        reportedCntCond(reportedCnt))
                .fetch();
    }

    private BooleanExpression nameCond(final String name) {
        return hasText(name) ? member.name.like("%" + name + "%") : null;
    }

    private BooleanExpression nicknameCond(final String nickname) {
        return hasText(nickname) ? member.nickname.like("%" + nickname + "%") : null;
    }

    private BooleanExpression emailCond(final String email) {
        return hasText(email) ? member.email.like("%" + email + "%") : null;
    }

    private BooleanExpression reportedCntCond(final Integer reportedCnt) {
        return reportedCnt != null ? member.reportedCnt.goe(reportedCnt) : null;
    }
}
