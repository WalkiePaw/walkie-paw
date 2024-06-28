package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberScoreResponse {
    private double Score;

    /**
     * Entity -> DTO
     */
    public static MemberScoreResponse from(Member member) {
        return new MemberScoreResponse(member.getRating());
    }
}
