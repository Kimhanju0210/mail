package org.example.mail.email.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class EmailTool {

    private final JavaMailSender mailSender;
    private final JavaMailSender javaMailSender;

    public void sendEmail(String email, String title, String content) {
        SimpleMailMessage message = createMessage(email, title, content);
        try {
            javaMailSender.send(message);
            log.info("발송 성공");
        } catch (Exception e) {
            log.error("발송 오류");
        }
    }

    private SimpleMailMessage createMessage(String toEmail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(content);

        return message;
    }
}
