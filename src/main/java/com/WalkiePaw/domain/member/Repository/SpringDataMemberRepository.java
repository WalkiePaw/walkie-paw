package com.WalkiePaw.domain.member.Repository;

import com.WalkiePaw.domain.member.entity.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("spring-data-jpa")
public interface SpringDataMemberRepository extends JpaRepository<Member, Integer>, MemberRepository {

    Optional<Member> findByUsername(String userName);

    boolean existsByUsername(String userName);
}
