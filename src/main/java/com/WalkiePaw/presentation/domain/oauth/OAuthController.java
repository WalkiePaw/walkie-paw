package com.WalkiePaw.presentation.domain.oauth;

import com.WalkiePaw.presentation.domain.oauth.dto.CodeRequest;
import com.WalkiePaw.presentation.domain.oauth.dto.OAuthUserinfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthProviderMappingHandler handler;

    @PostMapping("/api/v1/login/oauth")
    public ResponseEntity<OAuthUserinfoResponse> getUserInfo(@RequestBody CodeRequest code) {
        return ResponseEntity.ok(handler.getUserinfoByProvider(code));
    }

}
