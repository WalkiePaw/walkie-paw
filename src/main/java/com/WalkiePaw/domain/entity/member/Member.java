package com.WalkiePaw.domain.entity.member;

import com.WalkiePaw.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Integer id;
    @Column(name = "member_name")
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    @Column(name = "addr")
    private String address;
    private String gender;
    private LocalDate birth;
    private String profile;
    @Column(columnDefinition = "POINT")
    private Point point;
    @Column(name = "member_photo")
    private String photo;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    private int reportedCnt;

    private Member(String name, String email, String password, String phoneNumber,
                  String address, String gender, LocalDate birth, String profile, String photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
        this.profile = profile;
        this.photo = photo;
        status = MemberStatus.GENERAL;
    }

    /**
     * Member 생성 메서드
     */
    public static Member createMember(String name, String email, String password, String phoneNumber,
                               String address, String gender, LocalDate birth, String profile, String photo) {
        return new Member(name, email, password, phoneNumber,
                address, gender, birth, profile, photo);
    }

    /**
     * 회원 탈퇴 로직
     */
    public void withdraw() {
        status = MemberStatus.WITHDRAWN;
    }

    /**
     * TODO
     *  - 비밀번호 암호화 메서드 구현
     *  - 회원 정지 로직 추가
     *  - update 메서드 추가
     *  - validation 추가
     */
}
