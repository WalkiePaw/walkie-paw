package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryOverride {

    List<Member> findBySearchCond(String name, String nickname, String email, Integer reportedCnt);

    Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);
}
