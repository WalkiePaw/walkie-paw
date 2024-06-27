package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberGetResponse {
    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birth;
    private String profile;
    private double rating;
    private String photo;
    private MemberStatus status;
    private int reportedCnt;

    public static MemberGetResponse from(Member member) {
        return new MemberGetResponse(member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getBirth(),
                member.getProfile(),
                member.getRating(),
                member.getPhoto(),
                member.getStatus(),
                member.getReportedCnt());
    }
}
