package com.WalkiePaw.presentation.domain.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class OAuthController {

    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final WebClient webClient;
    private final String redirectUri;

    public OAuthController(
            @Value("${kakao.auth.token-url}") final String tokenUrl,
            @Value("${kakao.auth.client-id}") final String clientId,
            @Value("${kakao.auth.client-secret}") final String clientSecret,
            @Value("${kakao.auth.redirect-uri}") final String redirectUri,
            final WebClient webClient) {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.webClient = webClient;
        this.redirectUri = redirectUri;
    }


    @PostMapping("api/v1/user-info")
    public String getUserInfo(@RequestBody CodeRequest code) {

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code.getCode());
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");


        String response = webClient.method(HttpMethod.POST)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("response = " + response);
        return "ok";
    }
}
