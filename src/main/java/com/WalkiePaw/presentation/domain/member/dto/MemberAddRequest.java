package com.WalkiePaw.presentation.domain.member.dto;

import com.WalkiePaw.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import lombok.ToString;

@Schema(description = "멤버 생성 요청 DTO")
@Getter
@AllArgsConstructor
@ToString
public class MemberAddRequest {

    @Schema(description = "이름")
    private String name;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "패스워드")
    private String password;
    @Schema(description = "전화번호")
    private String phoneNumber;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "생년월일")
    private LocalDate birth;
    @Schema(description = "자기소개")
    private String profile;
    @Schema(description = "사진")
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
                .memberAddress(this.address)
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
                member.getMemberAddress(),
                member.getBirth(),
                member.getProfile(),
                member.getPhoto()
        );
    }
}
