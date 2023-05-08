package org.biryukov.sharebill.service.jdbcrepo.pojo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GlobalSessionMapper implements RowMapper<GlobalSession> {
    @Override
    public GlobalSession mapRow(ResultSet rs, int rowNum) throws SQLException {
        GlobalSession globalSession = new GlobalSession();
        globalSession.setId((UUID) rs.getObject("id"));
        globalSession.setGlobalSessionId((UUID) rs.getObject("global_session_id"));
        return globalSession;
    }
}
