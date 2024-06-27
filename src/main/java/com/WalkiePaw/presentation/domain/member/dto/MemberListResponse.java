package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberListResponse {
    private String name;
    private String nickname;
    private String email;
    private MemberStatus status;
    private int reportedCnt;

    public static MemberListResponse from(Member member) {
        return new MemberListResponse(
                member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getStatus(),
                member.getReportedCnt()
        );
    }
}
