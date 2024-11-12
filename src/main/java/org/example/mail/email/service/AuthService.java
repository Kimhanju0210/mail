package org.example.mail.email.service;


import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.example.mail.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final EmailTool emailTool;
    private static final String AUTH_CODE = "AUTH_CODE";

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;


}
