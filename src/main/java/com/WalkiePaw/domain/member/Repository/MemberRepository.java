package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Profile("spring-data-jpa")
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryOverride {

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);
}
