package com.WalkiePaw.presentation.domain.auth.dto;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignUpRequest {

    private String name;
    @Setter
    private String password;


    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .password(this.password)
                .build();
    }
}
