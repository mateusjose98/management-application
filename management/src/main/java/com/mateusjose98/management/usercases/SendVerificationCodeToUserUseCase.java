package com.mateusjose98.management.usercases;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SendVerificationCodeToUserUseCase {

    public void execute(UserDTO user) {
        log.info("Enviando código de verificação para {}", user.getPhone());
    }
}
