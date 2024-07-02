package com.WalkiePaw.presentation.domain.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class KakaoOAuthProvider {

    private final String tokenUri;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String userinfoUri;
    private final RestTemplate restTemplate;

    public KakaoOAuthProvider(
            @Value("${kakao.auth.userinfo-uri}") final String userinfoUri,
            @Value("${kakao.auth.token-uri}") final String tokenUri,
            @Value("${kakao.auth.client-id}") final String clientId,
            @Value("${kakao.auth.client-secret}") final String clientSecret,
            @Value("${kakao.auth.redirect-uri}") final String redirectUri,
            final RestTemplate restTemplate) {
        this.tokenUri = tokenUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.userinfoUri = userinfoUri;
        this.restTemplate = restTemplate;
    }

    public Optional<KakaoUserInfoResponse> getUserInfo(final String code) {
        String accessToken = getAccessToken(code).orElseThrow().getAccessToken();
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserInfoResponse> userInfo = restTemplate.exchange(
                userinfoUri,
                HttpMethod.GET,
                userInfoRequest,
                KakaoUserInfoResponse.class
        );
        return Optional.ofNullable(userInfo.getBody());
    }

    private Optional<OauthAccessToken> getAccessToken(final String code) {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(clientId, clientSecret);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final LinkedMultiValueMap<String, String> params = setParams(code);

        HttpEntity<LinkedMultiValueMap<String, String>> linkedMultiValueMapHttpEntity = new HttpEntity<>(params, httpHeaders);


        ResponseEntity<OauthAccessToken> token = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                linkedMultiValueMapHttpEntity,
                OauthAccessToken.class
        );
        return Optional.ofNullable(token.getBody());
    }


    private LinkedMultiValueMap<String, String> setParams(String code) {
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        return params;
    }

}
