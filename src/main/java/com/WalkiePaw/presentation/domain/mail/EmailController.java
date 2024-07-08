package com.WalkiePaw.presentation.domain.mail;

import com.WalkiePaw.domain.mail.service.MailService;
import com.WalkiePaw.presentation.domain.mail.dto.EmailAuthRequest;
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
        return ResponseEntity.ok(mailService.joinEmail(request.getEmail()));
    }

    @PostMapping("/authCheck")
    public ResponseEntity<Void> AuthCheck(@RequestBody @Valid final EmailAuthRequest request) {
        Boolean Checked = mailService.CheckAuthNum(request.getEmail(), request.getAuthNum());
        if (Checked) {
            return ResponseEntity.ok().build();
        } else {
            throw new NullPointerException("뭔가 잘못!");
        }
    }
}
