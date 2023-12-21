package com.mateusjose98.management.usercases;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.api.UserDTOMapper;
import com.mateusjose98.management.model.User;
import com.mateusjose98.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindUserByIdUseCase {

    final UserRepository<User> userRepository;

    public UserDTO execute(Long userId) {
        User user = userRepository.get(userId);
        return UserDTOMapper.fromUser(user);
    }
}
