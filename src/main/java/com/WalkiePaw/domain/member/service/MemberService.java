package com.WalkiePaw.domain.member.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.member.dto.MemberRequest;
import com.WalkiePaw.presentation.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(final Integer memberId) {
        return MemberResponse.from(memberRepository.findById(memberId));
    }

    public Integer save(final MemberRequest request) {
        Member member = request.toEntity();
        return memberRepository.save(member);
    }

    public void update(final Integer id, final MemberRequest request) {
        memberRepository.update(id, request.toEntity());
    }

    public void delete(final Integer id) {
        memberRepository.delete(id);
    }
}
