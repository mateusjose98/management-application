package com.mateusjose98.management.commons;

import com.mateusjose98.management.api.UserDTO;
import com.mateusjose98.management.model.UserPrincipal;
import org.springframework.security.core.Authentication;


public class UserUtils {
    public static UserDTO getAuthenticatedUser(Authentication authentication) {
        return ((UserDTO) authentication.getPrincipal());
    }

    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getDtoFromUser();
    }
}
