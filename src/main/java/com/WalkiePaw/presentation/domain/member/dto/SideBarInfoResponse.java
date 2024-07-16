package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SideBarInfoResponse {
    private String name;
    private int recruitCnt;
    private int researchCnt;
    private double score;
    private String photo;

    /**
     * Entity -> DTO
     */
    public static SideBarInfoResponse from(Member member) {
        return SideBarInfoResponse.builder()
                .name(member.getName())
                .recruitCnt(member.getRecruitCnt())
                .researchCnt(member.getResearchCnt())
                .score(member.getRating())
                .photo(member.getPhoto())
                .build();
    }
}
