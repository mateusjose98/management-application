package com.mateusjose98.management.repository;

import com.mateusjose98.management.model.Role;
import com.mateusjose98.management.model.User;
import com.mateusjose98.management.repository.rowmapper.UserRowMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static com.mateusjose98.management.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRepository<Role> roleRepository;

    @Override
    public User create(User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = getSqlParameterSource(user);
        user.setEnabled(false);
        user.setNotLocked(true);
        jdbcTemplate.update(INSERT_USER_QUERY, parameters, holder);
        user.setId(requireNonNull(holder.getKey()).longValue());
        return user;
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_QUERY, Map.of("id", id), new UserRowMapper());
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Integer existsByEmail(String email) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_QUERY, Map.of("email", email), Integer.class);
    }

    public void createUserVerification(Long userId, String verificationUrl) {
        jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", userId, "url", verificationUrl));
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_QUERY, Map.of("email", email),  new UserRowMapper());
    }


    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());
    }
}
