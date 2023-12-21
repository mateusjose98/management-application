package com.mateusjose98.management.services;

import com.mateusjose98.management.model.Role;
import com.mateusjose98.management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Log4j2
@RequiredArgsConstructor
public class RoleService {

    final RoleRepository<Role> roleRepository;
    public Role getRoleByUserId(Long id) {
        Role roleByUserId = roleRepository.getRoleByUserId(id);
        return roleByUserId;
    }

    public Collection<Role> getRoles() {
        return roleRepository.list();
    }

}
