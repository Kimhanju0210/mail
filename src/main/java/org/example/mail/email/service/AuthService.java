package org.example.mail.email.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.mail.CustomException;
import org.example.mail.ErrorCode;
import org.example.mail.redis.service.RedisTool;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.mail.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RedisTool redisTool;
    private final EmailTool emailTool;

    private static final String AUTH_CODE = "AUTH_CODE";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void sendEmail(String toEmail) {
        if(userRepository.existsByEmail(toEmail))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);

        String title = "유저 이메일 인증 번호";
        String authCode = this.createCode();
        redisTool.setValues(AUTH_CODE + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
        emailTool.sendEmail(toEmail, title, authCode);
    }


    public boolean authCode(String email, String authCode) {
        String redisAuthCode = redisTool.getValues(AUTH_CODE + email);

        return redisTool.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

}
