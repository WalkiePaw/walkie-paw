package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberAddRequest {

    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private LocalDate birth;
    private String profile;
    private String photo;

    /**
     * request 객체를 jpa entity 객체로 바꿔주는 메서드?
     */
    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .birth(this.birth)
                .profile(this.profile)
                .photo(this.photo)
                .build();
    }

    public static MemberAddRequest from(Member member) {
        return new MemberAddRequest(
                member.getName(),
                member.getNickname(),
                member.getEmail(),
                member.getPassword(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getBirth(),
                member.getProfile(),
                member.getPhoto()
        );
    }
}
