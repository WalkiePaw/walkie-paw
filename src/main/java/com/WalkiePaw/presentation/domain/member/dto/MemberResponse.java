package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor/*(acess)*/
public class MemberResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate birth;
    private String profile;
    private Point point;
    private String photo;
    private MemberStatus status;
    private int reportedCnt;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.name = member.getName();
        memberResponse.email = member.getEmail();
        memberResponse.phoneNumber = member.getPhoneNumber();
        memberResponse.address = member.getAddress();
        memberResponse.gender = member.getGender();
        memberResponse.birth = member.getBirth();
        memberResponse.profile = member.getProfile();
        memberResponse.status = member.getStatus();
        memberResponse.reportedCnt = member.getReportedCnt();
        return memberResponse;
    }
}
