package com.WalkiePaw.presentation.domain.mail;

import com.WalkiePaw.domain.mail.service.MailService;
import com.WalkiePaw.presentation.domain.mail.dto.EmailAuthRequest;
import com.WalkiePaw.presentation.domain.mail.dto.EmailAuthResponse;
import com.WalkiePaw.presentation.domain.mail.dto.EmailSendRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class EmailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> mailSend(@RequestBody @Valid final EmailSendRequest request) {
        mailService.joinEmail(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authCheck")
    public ResponseEntity<EmailAuthResponse> AuthCheck(@RequestBody @Valid final EmailAuthRequest request) {
        return ResponseEntity.ok(mailService.CheckAuthNum(request.getEmail(), request.getAuthNum()));

    }
}
