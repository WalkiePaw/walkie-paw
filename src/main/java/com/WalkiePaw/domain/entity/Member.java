package com.WalkiePaw.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(name = "member_name")
    private String name;
    private String email;
    private String password;
    @Column(name = "tel")
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate createdDate;
    private LocalDate birth;
    private String profile;
    @Column(name = "member_photo")
    private String photo;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    @Column(name = "withdrawn_date")
    private LocalDate withdrawnDate;
    private int reportedCount;

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
        createdDate = LocalDate.now();
    }

    /**
     * Member 생성 메서드
     * @param name
     * @param email
     * @param password
     * @param phoneNumber
     * @param address
     * @param gender
     * @param birth
     * @param profile
     * @param photo
     * @return
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
        withdrawnDate = LocalDate.now();
    }

    /**
     * TODO
     *  - 비밀번호 암호화 메서드 구현
     *  - 회원 정지 로직 추가
     *  - update 메서드 추가
     */
}
