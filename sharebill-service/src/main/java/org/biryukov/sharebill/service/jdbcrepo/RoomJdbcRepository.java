package org.biryukov.sharebill.service.jdbcrepo;

import org.biryukov.sharebill.service.jdbcrepo.pojo.Room;
import org.biryukov.sharebill.service.jdbcrepo.pojo.RoomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RoomJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createRoom(String room) {
        jdbcTemplate.update("insert into sharebill.room(id, room) values(?, ?)"
                ,UUID.randomUUID(), room);
    }

    public List<Room> findRoomByGlobalSession(UUID globalSessionId) {
        RoomRowMapper mapper = new RoomRowMapper();

        return jdbcTemplate.query("SELECT DISTINCT r.id, r.room FROM sharebill.room r " +
                        "INNER JOIN sharebill.person p ON r.id = p.room_id " +
                        "INNER JOIN sharebill.global_session gs ON p.global_session_id = gs.id " +
                        "where gs.global_session_id = ?"
                ,mapper, globalSessionId);
    }

}
