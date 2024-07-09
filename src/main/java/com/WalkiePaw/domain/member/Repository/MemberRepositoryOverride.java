package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryOverride {

    List<Member> findBySearchCond(String name, String nickname, String email, Integer reportedCnt);


}
