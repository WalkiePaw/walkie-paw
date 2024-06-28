package com.WalkiePaw.presentation.domain.member.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MemberPasswdUpdateRequest {
    private String password;
}
