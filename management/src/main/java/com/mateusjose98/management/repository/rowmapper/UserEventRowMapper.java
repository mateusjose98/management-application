package com.mateusjose98.management.repository.rowmapper;



import com.mateusjose98.management.model.UserEvent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserEventRowMapper implements RowMapper<UserEvent> {
    @Override
    public UserEvent mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserEvent.builder()
                .id(resultSet.getLong("id"))
                .event(resultSet.getString("type"))
                .description(resultSet.getString("description"))
                .ipAddress(resultSet.getString("ip_address"))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
