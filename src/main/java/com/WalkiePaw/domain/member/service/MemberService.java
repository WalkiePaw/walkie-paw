package com.WalkiePaw.domain.member.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.member.EmailVerifyRequest;
import com.WalkiePaw.presentation.domain.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberListResponse> findAll() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(MemberListResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberGetResponse findById(final Integer memberId) {
        return MemberGetResponse.from(memberRepository.findById(memberId).orElseThrow());
    }

    public Integer save(final MemberAddRequest request) {
        Member member = request.toEntity();
        return memberRepository.save(member).getId();
    }

    public void update(final Integer id, final MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateMember(request);
    }

    public MemberScoreResponse getMemberScore(final Integer memberId) {
        return MemberScoreResponse.from(memberRepository.findById(memberId).orElseThrow());
    }

    public MemberRRCountResponse getMemberRRCount(final Integer memberId) {
        return MemberRRCountResponse.from(memberRepository.findById(memberId).orElseThrow());
    }

    public void updatePasswd(final Integer memberId, final MemberPasswdUpdateRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updateMemberPasswd(request);
    }

    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public void draw(final Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.withdraw();
    }

    public EmailVerifyResponse verifyEmail(final EmailVerifyRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            return new EmailVerifyResponse(true);
        } else {
            return new EmailVerifyResponse(false);
        }
    }
}
