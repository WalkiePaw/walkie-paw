package com.WalkiePaw.presentation.domain.member;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private static final String MEMBER_URL = "/members/";

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> responses = null;
        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        MemberResponse response = null;
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Validated @RequestBody MemberRequest request) {
        /**
         * memberRequest를 저장하는 서비스
         */
        return ResponseEntity.created(URI.create(MEMBER_URL/* + memberId*/)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long id) {
        /**
         * id에 해당하는 데이터를 삭제하는 서비스
         */
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<MemberResponse> updateMember(@Validated @RequestBody MemberRequest request) {
        /**
         * memberRequest로 수정하는 서비스
         */
        return ResponseEntity.noContent().build();
    }


}
