package org.biryukov.sharebill.service.jdbcrepo;

import org.biryukov.sharebill.service.jdbcrepo.pojo.GlobalSession;
import org.biryukov.sharebill.service.jdbcrepo.pojo.GlobalSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GlobalSessionJdbcRepository {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void createGlobalSession(UUID globalSessionId) {
        jdbcTemplate.update("insert into sharebill.global_session(id, created_ts, global_session_id) values(?, now(), ?)"
                , UUID.randomUUID(), globalSessionId);
    }

    public List<GlobalSession> findByGlobalSessionId(UUID globalSessionId) {
        GlobalSessionMapper mapper = new GlobalSessionMapper();
        return jdbcTemplate.query("select * from sharebill.global_session where global_session_id = ?"
                , mapper, globalSessionId);
    }
}
