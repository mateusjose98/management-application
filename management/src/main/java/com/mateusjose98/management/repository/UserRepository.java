package com.mateusjose98.management.repository;

import com.mateusjose98.management.model.User;

import java.util.Collection;

public interface UserRepository<T extends User> {

    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
    Integer existsByEmail(String lowerCase);
    void createUserVerification(Long userId, String verificationUrl);

    User getUserByEmail(String email);
}
