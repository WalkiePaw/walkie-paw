package com.WalkiePaw.presentation.domain.member;

import com.WalkiePaw.domain.member.service.MemberService;
import com.WalkiePaw.presentation.domain.member.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
        Integer memberId = memberService.save(request);
        return ResponseEntity.created(URI.create(MEMBER_URL + memberId)).build();
    }

    @ApiResponse(responseCode = "204", description = "수정됨")
    @Operation(summary = "멤버 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMember(final @PathVariable("id") Integer memberId, final @RequestBody MemberUpdateRequest request) {
        memberService.update(memberId, request);
        return ResponseEntity.noContent().build();
    }

//    public ResponseEntity<MemberResponse> deleteMember(final @PathVariable("id") Integer memberId) {
//        memberService.delete(memberId);
//        return ResponseEntity.noContent().build();
//    }


}
