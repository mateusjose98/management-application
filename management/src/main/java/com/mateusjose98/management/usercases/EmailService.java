package com.mateusjose98.management.usercases;

import com.mateusjose98.management.model.enums.VerificationType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {
    public void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType) {
        log.info("Enviando email para {} com link {}", email, verificationUrl);
    }
}
