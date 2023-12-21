package com.mateusjose98.management.repository;

import com.mateusjose98.management.exceptions.ResourceNotFoundException;
import com.mateusjose98.management.model.Role;
import com.mateusjose98.management.repository.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static com.mateusjose98.management.query.RoleQuery.*;
import static java.util.Map.of;

import static com.mateusjose98.management.model.enums.RoleType.ROLE_USER;

import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Log4j2
public class RoleRepositoryImpl implements RoleRepository<Role> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list() {
        log.info("Fetching all roles");
        try {
            return jdbc.query(SELECT_ROLES_QUERY, new RoleRowMapper());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
    }


    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Role não encontrada: " + ROLE_USER.name());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
    }

    @Override
    public void addRoleToUser(Long userId, Role role) {
        log.info("Adding role {} to user id: {}", role, userId);
        try {
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Role não encontrada: " + ROLE_USER.name());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        log.info("Fetching role for user id: {}", userId);
        try {
            return jdbc.queryForObject(SELECT_ROLE_BY_ID_QUERY, of("id", userId), new RoleRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        log.info("Updating role for user id: {}", userId);
        try {
            Role role = getByName(roleName);
            jdbc.update(UPDATE_USER_ROLE_QUERY, of("roleId", role.getId(), "userId", userId));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("No role found by name: " + roleName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getByName(String roleName) {
       return jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
    }
}
