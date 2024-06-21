package com.WalkiePaw.presentation.domain.member;

import com.WalkiePaw.domain.member.entity.MemberStatus;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Getter
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
}
