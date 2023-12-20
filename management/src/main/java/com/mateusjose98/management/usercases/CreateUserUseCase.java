package com.mateusjose98.management.usercases;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.api.UserDTOMapper;
import com.mateusjose98.management.exceptions.CreateUserException;
import com.mateusjose98.management.exceptions.ResourceAlreadyExistsException;
import com.mateusjose98.management.model.Role;
import com.mateusjose98.management.model.User;
import com.mateusjose98.management.model.enums.VerificationType;
import com.mateusjose98.management.repository.RoleRepository;
import com.mateusjose98.management.repository.UserRepository;
import com.mateusjose98.management.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.mateusjose98.management.commons.Utils.getVerificationUrl;
import static com.mateusjose98.management.model.enums.RoleType.ROLE_USER;
import static com.mateusjose98.management.model.enums.VerificationType.ACCOUNT;
import static java.util.Map.of;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CreateUserUseCase {

    final UserRepository<User> userRepository;
    final RoleRepository<Role> roleRepository;
    final BCryptPasswordEncoder encoder;
    final EmailService emailService;


    public UserDTO execute(User user) {
        boolean emailAlreadyExists = userRepository.existsByEmail(user.getEmail().trim().toLowerCase()) > 0;
        if (emailAlreadyExists) throw new ResourceAlreadyExistsException(String.format("Usuário com email %s já existe!", user.getEmail()));
        try {

            String encoded = encoder.encode(user.getPassword().trim());
            user.setPassword(encoded);
            User saved = userRepository.create(user);
            String roleName = ROLE_USER.name();
            Role role = roleRepository.getByName(roleName);
            roleRepository.addRoleToUser(user.getId(), roleName);
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            userRepository.createUserVerification(user.getId(), verificationUrl);
            sendEmail(user.getFirstName(), user.getEmail(), verificationUrl);
            return UserDTOMapper.fromUser(saved, role);
        }catch ( Exception ex) {
            throw new CreateUserException(ex.getMessage());
        }
    }

    private void sendEmail(String firstName, String email, String verificationUrl) {
        CompletableFuture.runAsync(() -> emailService.sendVerificationEmail(firstName, email, verificationUrl, VerificationType.ACCOUNT));
    }



}
