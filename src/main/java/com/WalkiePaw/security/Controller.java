package com.WalkiePaw.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

  private static final Logger log = LoggerFactory.getLogger(Controller.class);

  @GetMapping("/jwt-test")
  public String jwtTest() {
    return "jwtTest 요청 성공";
  }

  @GetMapping("/test")
  public String test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    log.info("userDetails: {}", userPrincipal);
    log.info("email : {}" ,userPrincipal.getEmail());
    log.info("userId: {}", userPrincipal.getMemberId());
    return "test";
  }
}