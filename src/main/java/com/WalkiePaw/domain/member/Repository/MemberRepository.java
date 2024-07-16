package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.SocialType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Profile("spring-data-jpa")
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryOverride {

    @Query("select m from Member m where m.email = :email and m.role = 'USER'")
    Optional<Member> findByEmail(@Param("email") String email);

    @Query("select m from Member m where m.nickname = :nickname")
    Optional<Member> findByNickname(@Param("nickname") String nickname);

    Optional<Member> findByEmailAndName(String email, String name);

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
