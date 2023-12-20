package com.mateusjose98.management.commons;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

public class Utils {

    public static String getVerificationUrl(String key, String type) {
        return fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }
}
