package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRRCountResponse {
    private int recruitCount;
    private int researchCount;

    /**
     * Entity -> DTO
     */
    public static MemberRRCountResponse from(Member member) {
        return new MemberRRCountResponse(member.getRecruitCnt(), member.getResearchCnt());
    }
}
