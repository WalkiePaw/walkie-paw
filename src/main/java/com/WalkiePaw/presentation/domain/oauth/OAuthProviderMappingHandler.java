package com.WalkiePaw.presentation.domain.oauth;

import com.WalkiePaw.presentation.domain.oauth.dto.CodeRequest;
import com.WalkiePaw.presentation.domain.oauth.dto.OAuthUserinfoResponse;
import com.WalkiePaw.presentation.domain.oauth.provider.KakaoOAuthProvider;
import com.WalkiePaw.presentation.domain.oauth.provider.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthProviderMappingHandler {

     private final Map<String, OAuthProvider> handler = new HashMap<>();

     public OAuthProviderMappingHandler(final KakaoOAuthProvider kakaoOAuthProvider) {
          handler.put("kakao", kakaoOAuthProvider);
     }

     public OAuthUserinfoResponse getUserinfoByProvider(final CodeRequest request) {
          OAuthProvider oAuthProvider = handler.get(request.getProvider());
          return oAuthProvider.getUserInfo(request.getCode());
     }
}
