package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    private String name;
    private String profile;
    private String member_photo;

    /**
     * Entity -> DTO
     */
    public static ProfileResponse from(Member member) {
        return ProfileResponse.builder()
                .name(member.getName())
                .profile(member.getProfile())
                .member_photo(member.getPhoto())
                .build();
    }
}
