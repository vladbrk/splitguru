package org.biryukov.sharebill.service.jdbcrepo.pojo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoomRowMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();
        room.setId((UUID) rs.getObject("id"));
        room.setRoom(rs.getString("room"));
        return room;
    }
}
