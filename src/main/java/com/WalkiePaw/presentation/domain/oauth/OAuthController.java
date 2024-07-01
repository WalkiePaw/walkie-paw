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

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OAuthController {

    private final String tokenUri;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String userinfoUri;

    public OAuthController(
        @Value("${kakao.auth.userinfo-uri}") final String userinfoUri,
        @Value("${kakao.auth.token-uri}") final String tokenUri,
        @Value("${kakao.auth.client-id}") final String clientId,
        @Value("${kakao.auth.client-secret}") final String clientSecret,
        @Value("${kakao.auth.redirect-uri}") final String redirectUri) {
        this.tokenUri = tokenUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.userinfoUri = userinfoUri;
    }


    @PostMapping("/api/v1/user-info")
    public ResponseEntity<KakaoInfoResponse> getAccessToken(@RequestBody CodeRequest code) {

        System.out.println("code = " + code);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(clientId, clientSecret);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code.getCode());
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<LinkedMultiValueMap<String, String>> linkedMultiValueMapHttpEntity = new HttpEntity<>(params, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OauthAccessToken> token = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            linkedMultiValueMapHttpEntity,
            OauthAccessToken.class
        );
        String accessToken = token.getBody().getAccessToken();
        System.out.println("accessToken = " + accessToken);

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<KakaoUserInfoResponse> userInfo = restTemplate.exchange(
            userinfoUri,
            HttpMethod.GET,
            userInfoRequest,
            KakaoUserInfoResponse.class
        );
        String email = userInfo.getBody().kakaoAccount.getEmail();
        String name = userInfo.getBody().kakaoAccount.getName();



        return ResponseEntity.ok(new KakaoInfoResponse(email, name));
    }
}
