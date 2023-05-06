package org.biryukov.sharebill.service.jdbcrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RoomJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createRoom(String room) {
        jdbcTemplate.update(
                "insert into sharebill.room(id, room) values(?, ?)"
                ,UUID.randomUUID(), room);
    }

}
