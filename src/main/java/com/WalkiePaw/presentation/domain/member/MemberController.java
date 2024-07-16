package com.WalkiePaw.presentation.domain.member;

import com.WalkiePaw.domain.member.service.MemberService;
import com.WalkiePaw.presentation.domain.member.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "members", description = "멤버 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final String MEMBER_URL = "/members/";

    @ApiResponse(responseCode = "200")
    @Operation(summary = "멤버 리스트")
    @GetMapping
    public ResponseEntity<List<MemberListResponse>> memberList() {
        return ResponseEntity.ok()
                .body(memberService.findAll());
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "멤버 조회")
    @GetMapping("/{id}")
    public ResponseEntity<MemberGetResponse> getMember(@Parameter(description = "멤버의 ID") final @PathVariable("id") Integer memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "멤버 추가")
    @PostMapping
    public ResponseEntity<Void> addMember(final @Validated @RequestBody MemberAddRequest request) {
        System.out.println("request = " + request);
        Integer memberId = memberService.save(request);
        return ResponseEntity.created(URI.create(MEMBER_URL + memberId)).build();
    }

    @Operation(summary = "소셜로그인 회원가입")
    @PostMapping("/social-signup")
    public ResponseEntity<Void> socialSignUp(final @Validated @RequestBody SocialSignUpRequest request) {
        Integer memberId = memberService.socialSignUp(request);
        return ResponseEntity.created(URI.create(MEMBER_URL + memberId)).build();
    }

    @ApiResponse(responseCode = "204", description = "수정됨")
    @Operation(summary = "멤버 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(final @PathVariable("id") Integer memberId, final @RequestBody MemberUpdateRequest request) {
        memberService.update(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "멤버 리뷰 평점 조회")
    @GetMapping("/{id}/score")
    public ResponseEntity<MemberScoreResponse> getMemberScore(@PathVariable("id") final Integer memberId) {
        return ResponseEntity.ok(memberService.getMemberScore(memberId));
    }

    @Operation(summary = "멤버 구인 구직 횟수 조회")
    @GetMapping("/{id}/RRCount")
    public ResponseEntity<MemberRRCountResponse> getMemberRRCount(@PathVariable("id") final Integer memberId) {
        return ResponseEntity.ok(memberService.getMemberRRCount(memberId));
    }

    @Operation(summary = "비밀번호 찾기 - 멤버 비밀번호 수정")
    @PatchMapping("/{id}/passwordUpdate")
    public ResponseEntity<Void> updateMemberPasswd(@PathVariable("id") final Integer memberId, @RequestBody final MemberPasswdUpdateRequest request) {
        memberService.updatePasswd(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마이페이지 - 멤버 비밀번호 수정")
    @PatchMapping("/mypage/{id}/password-update")
    public ResponseEntity<Void> mypageUpdateMemberPasswd(@PathVariable("id") final Integer memberId, @RequestBody final MemberPasswdUpdateRequest request) {
        memberService.updatePasswd(memberId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "멤버 탈퇴")
    @PatchMapping("/{id}/draw")
    public ResponseEntity<Void> withDraw(@PathVariable("id") final Integer memberId) {
        memberService.draw(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "멤버 정지")
    @PatchMapping("/{id}/ban")
    public ResponseEntity<Void> banMember(@PathVariable("id") final Integer memberId) {
        memberService.ban(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "멤버 복구")
    @PatchMapping("{id}/general")
    public ResponseEntity<Void> generalMember(@PathVariable("id") final Integer memberId) {
        memberService.general(memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "멤버 검색")
    @GetMapping("/search")
    public ResponseEntity<Page<MemberListResponse>> search(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String nickname,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final Integer reportedCnt,
            Pageable pageable
    ) {
        Page<MemberListResponse> list = memberService.findBySearchCond(name, nickname, email, reportedCnt, pageable);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/check-nickname")
    public ResponseEntity<NicknameCheckResponse> checkNickname(
            @RequestParam final String nickname
    ) {
        return ResponseEntity.ok(memberService.NicknameCheck(nickname));
    }

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponse> checkEmail(
            @RequestParam final String email
    ) {
        return ResponseEntity.ok(memberService.EmailCheck(email));
    }

    @Operation(summary = "이메일 찾기")
    @PostMapping("/find-email")
    public ResponseEntity<FindEmailResponse> findEmail(@RequestBody final FindEmailRequest request) {
        return ResponseEntity.ok(memberService.findEmail(request));
    }

    @Operation(summary = "비밀번호 찾기")
    @PostMapping("/find-passwd")
    public ResponseEntity<FindPasswdResponse> findPasswd(@RequestBody final FindPasswdRequest request) {
        return ResponseEntity.ok(memberService.findPasswd(request));
    }

    @Operation(summary = "프로파일")
    @GetMapping("/{nickname}/dashboard")
    public ResponseEntity<ProfileResponse> profile(@PathVariable("nickname") final String nickname) {
        return ResponseEntity.ok(memberService.findProfile(nickname));
    }

    @Operation(summary = "마이페이지 - 주소 선택? 동네 설정?")
    @PatchMapping("/{id}/selected-addresses")
    public ResponseEntity<Void> updateSelectedAddresses(
        @PathVariable("id") final Integer memberId, @RequestBody final UpdateSelectedAddrRequest request ) {
        memberService.updateSeletedAddr(memberId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지 - 내 주소, 선택 주소 요청")
    @GetMapping("/{id}/addresses")
    public ResponseEntity<AddressesGetResponse> getAddresses(@PathVariable("id") final Integer memberId) {
        return ResponseEntity.ok(memberService.getAddressesByMemberId(memberId));
    }

    @Operation(summary = "마이페이지 - 좌측 사이드바 사용자 데이터 요청")
    @GetMapping("/{id}/sidebar-info")
    public ResponseEntity<SideBarInfoResponse> getSideBarInfo(@PathVariable("id") final Integer memberId) {
        return ResponseEntity.ok(memberService.getSidebarinfoBy(memberId));
    }
}
