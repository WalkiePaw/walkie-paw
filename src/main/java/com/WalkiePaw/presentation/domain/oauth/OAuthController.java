package com.WalkiePaw.presentation.domain.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OAuthController {

    private final String tokenUri;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String userinfoUri;
    private final RestTemplate restTemplate;

    public OAuthController(
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


    @PostMapping("/api/v1/user-info")
    public ResponseEntity<KakaoUserInfoResponse> getKakaoUserInfo(@RequestBody CodeRequest code) {


        // 예외 핸들리 필요
        String accessToken = getAccessToken(code.getCode())
                .orElseThrow().getAccessToken();

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);
        return restTemplate.exchange(
            userinfoUri,
            HttpMethod.GET,
            userInfoRequest,
            KakaoUserInfoResponse.class
        );
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
