package com.mateusjose98.management.repository;

import com.mateusjose98.management.model.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    /* Basic CRUD Operations */
    T create(T data);
    Collection<T> list();
//    T get(Long id);
//    T update(T data);
//    Boolean delete(Long id);

    /* More Complex Operations */
    void addRoleToUser(Long userId, String roleName);
    void addRoleToUser(Long userId, Role roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
    Role getByName(String roleName);
}
