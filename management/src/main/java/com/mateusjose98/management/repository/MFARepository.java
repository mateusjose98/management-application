package com.mateusjose98.management.repository;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.model.User;

public interface MFARepository {

    void sendVerificationCodeToUser(UserDTO user);
}
