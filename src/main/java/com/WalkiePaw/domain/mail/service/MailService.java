package com.WalkiePaw.domain.mail.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.global.exception.BadRequestException;
import com.WalkiePaw.presentation.domain.mail.dto.EmailAuthResponse;
import com.WalkiePaw.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.WalkiePaw.global.exception.ExceptionCode.DUPLICATED_EMAIL;
import static com.WalkiePaw.global.exception.ExceptionCode.MAIL_SEND_FAIL;
import static com.WalkiePaw.global.exception.ExceptionCode.NOT_FOUND_EMAIL;

@Service
@RequiredArgsConstructor
public class MailService {

    private final RedisUtil redisUtil;
    private final JavaMailSender mailSender;
    private int authNumber;
    private final MemberRepository memberRepository;

    // 인증번호 검증 메서드
    public EmailAuthResponse CheckAuthNum(String email, String authNum) {
        if (redisUtil.getData(authNum) == null) { // 검증 실패
            return EmailAuthResponse.builder()
                    .result("Wrong AuthNum")
                    .build();
        } else if (redisUtil.getData(authNum).equals(email)) { // 검증 성공
            return buildEmailAuthResponse(email);
        } else {
            return EmailAuthResponse.builder()
                    .result("Something Wrong")
                    .build();
        }
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage(); // JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom); // 이메일의 발신자 주소 설정
            helper.setTo(toMail); // 이메일의 수신자 주소 설정
            helper.setSubject(title); // 이메일의 제목을 설정
            helper.setText(content, true); // 이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정
            mailSender.send(message);
        } catch (MessagingException e) { // 이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            throw new BadRequestException(MAIL_SEND_FAIL);
//            e.printStackTrace(); // e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
        redisUtil.setDataExpire(Integer.toString(authNumber), toMail, 60 * 5L); // redis db에 인증번호와 이메일을 하나의 쌍으로, 유효기간을 지정하여 저장.
    }


    // mail을 어디서, 어디로 보내는지, 인증 번호를 html 형식으로 어떻게 보내는지 작성
    public String joinEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new BadRequestException(DUPLICATED_EMAIL);
                });
        makeRandomNumber();
        String setFrom = "no.reply.walkiepaw@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email;
        String title = "인증 이메일 입니다."; // 이메일 제목
        String content =
                "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //이메일 내용 삽입
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    //임의의 6자리 양수를 반환
    public Integer makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for (int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
        return authNumber;
    }

    /**
     * 이메일 인증에 성공했을 때 호출되는 메서드
     * db에 해당하는 email이 존재하면 해당 email의 memberId를 포함한 EmailAuthResponse를 만듦.
     * 만약 없다면 memberId가 빈 EmailAuthResponse를 만듦.
     */
    private EmailAuthResponse buildEmailAuthResponse(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
            () -> new BadRequestException(NOT_FOUND_EMAIL)
        );
        return EmailAuthResponse.from(member);
    }
}
