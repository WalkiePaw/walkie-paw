package com.WalkiePaw.domain.member.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.member.dto.MemberRequest;
import com.WalkiePaw.presentation.domain.member.dto.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    MemberRepository memberRepository;

    public List<MemberResponse> findAll() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    public MemberResponse findById(Integer memberId) {
        return MemberResponse.from(memberRepository.findById(memberId));
    }

    public Integer save(MemberRequest request) {
        Member member = request.toEntity();
        return memberRepository.save(member);
    }

    public void update(Integer id, MemberRequest request) {
        memberRepository.update(id, request.toEntity());
    }

    public void delete(Integer id) {
        memberRepository.delete(id);
    }
}
