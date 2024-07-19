package com.WalkiePaw.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private final String mailPassword;
    private final String senderEmail;

    public EmailConfig(@Value("${google.mail.password}") final String mailPassword, @Value("${google.mail.email}") final String senderEmail) {
        this.mailPassword = mailPassword;
        this.senderEmail = senderEmail;
    }

    @Bean
    public JavaMailSender mailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // 이메일 전송에 사용할 SMTP 서버 호스트
        mailSender.setPort(587);
        mailSender.setUsername(senderEmail); // 구글계정
        mailSender.setPassword(mailPassword); // 구글 앱 비밀번호, properties 처리

        Properties javaMailProperties = new Properties(); // JavaMail의 속성을 설정하기 위해 Properties 객체를 생성
        javaMailProperties.put("mail.transport.protocol", "smtp"); // 프로토콜로 smtp 사용
        javaMailProperties.put("mail.smtp.auth", "true"); // smtp 서버에 인증이 필요
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL 소켓 팩토리 클래스 사용
        javaMailProperties.put("mail.smtp.starttls.enable", "true"); // STARTTLS(TLS를 시작하는 명령)를 사용하여 암호화된 통신을 활성화
        javaMailProperties.put("mail.debug", "true"); // 디버깅 정보 출력
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.naver.com"); // smtp 서버의 ssl 인증서를 신뢰
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 사용할 ssl 프로토콜 버젼

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}