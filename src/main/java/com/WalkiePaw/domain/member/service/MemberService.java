package com.WalkiePaw.domain.member.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.presentation.domain.member.dto.*;
import com.WalkiePaw.security.CustomPasswordEncoder;
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
    private final CustomPasswordEncoder passwordEncoder;

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
        passwordEncoder.encodePassword(member);
        return memberRepository.save(member).getId();
    }

    public void update(final Integer id, final MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateMember(request);
    }

    @Transactional(readOnly = true)
    public MemberScoreResponse getMemberScore(final Integer memberId) {
        return MemberScoreResponse.from(memberRepository.findById(memberId).orElseThrow());
    }

    @Transactional(readOnly = true)
    public MemberRRCountResponse getMemberRRCount(final Integer memberId) {
        return MemberRRCountResponse.from(memberRepository.findById(memberId).orElseThrow());
    }

    public void updatePasswd(final Integer memberId, final MemberPasswdUpdateRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updatePasswd(request.getPassword());
        passwordEncoder.encodePassword(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public void draw(final Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.withdraw();
    }

    public void ban(final Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.ban();
    }

    public void general(final Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.general();
    }

    public List<MemberListResponse> findBySearchCond(final String name, final String nickname, final String email, final Integer reportedCnt) {
        List<Member> list = memberRepository.findBySearchCond(name, nickname, email, reportedCnt);
        return list.stream()
                .map(MemberListResponse::from)
                .toList();
    }

    public NicknameCheckResponse NicknameCheck(final String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isEmpty()) {
            return new NicknameCheckResponse(NCheckResult.AVAILABLE);
        } else {
            return new NicknameCheckResponse(NCheckResult.DUPLICATED);
        }
    }
}
