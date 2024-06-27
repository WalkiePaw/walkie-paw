package com.WalkiePaw.presentation.domain.member;

import com.WalkiePaw.domain.member.service.MemberService;
import com.WalkiePaw.presentation.domain.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final String MEMBER_URL = "/members/";

    @GetMapping
    public ResponseEntity<List<MemberListResponse>> memberList() {
        return ResponseEntity.ok()
                .body(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberGetResponse> getMember(final @PathVariable("id") Integer memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @PostMapping
    public ResponseEntity<Void> addMember(final @Validated @RequestBody MemberAddRequest request) {
        Integer memberId = memberService.save(request);
        return ResponseEntity.created(URI.create(MEMBER_URL + memberId)).build();
    }

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
