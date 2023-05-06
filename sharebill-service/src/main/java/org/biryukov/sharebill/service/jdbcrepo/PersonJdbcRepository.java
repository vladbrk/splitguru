package org.biryukov.sharebill.service.jdbcrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class PersonJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPerson(String name, String room) {
        UUID roomId = jdbcTemplate.queryForObject("select id from sharebill.room where room = ?", UUID.class, room);
        jdbcTemplate.update(
                "insert into sharebill.person(id, name, room_id) value(?, ?, ?)",
                UUID.randomUUID(),
                name,
                roomId);
    }

    public List<Person> findPerson(String room) {
        PersonRowMapper mapper = new PersonRowMapper();
        List<Person> persons = jdbcTemplate.query("select p.id as id, p.name as name from sharebill.person p join sharebill.room r on p.room_id = r.id where r.room = ?", mapper, room);
        return persons;
    }
}

