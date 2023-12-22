package com.mateusjose98.management.usercases;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.model.User;
import com.mateusjose98.management.repository.MFARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SendVerificationCodeToUserUseCase {

    final MFARepository mfaRepository;
    public void execute(UserDTO user) {
        log.info("Enviando código de verificação para {}", user.getPhone());
        mfaRepository.sendVerificationCodeToUser(user);
    }
}
