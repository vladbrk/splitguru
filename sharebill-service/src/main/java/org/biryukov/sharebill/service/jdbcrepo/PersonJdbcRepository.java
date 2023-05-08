package org.biryukov.sharebill.service.jdbcrepo;

import org.biryukov.sharebill.service.jdbcrepo.pojo.Person;
import org.biryukov.sharebill.service.jdbcrepo.pojo.PersonRowMapper;
import org.biryukov.sharebill.service.jdbcrepo.pojo.RoomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PersonJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPerson(String name, UUID roomId) {
        jdbcTemplate.update(
                "insert into sharebill.person(id, name, room_id) value(?, ?, ?)",
                UUID.randomUUID(),
                name,
                roomId);
    }

    public List<Person> findPerson(UUID roomId) {
        PersonRowMapper mapper = new PersonRowMapper();
        List<Person> persons = jdbcTemplate.query(
                "select p.id as id, p.name as name from sharebill.person p " +
                        "join sharebill.room r on p.room_id = r.id " +
                        "where r.id = ?",
                mapper,
                roomId);
        return persons;
    }

    public List<Person> findPersonsByGlobalSession(UUID roomId, UUID globalSessionId) {
        PersonRowMapper mapper = new PersonRowMapper();

        return jdbcTemplate.query("SELECT p.id, p.name FROM sharebill.person p " +
                        "INNER JOIN sharebill.room r ON r.id = p.room_id " +
                        "INNER JOIN sharebill.global_session gs ON p.global_session_id = gs.id " +
                        "WHERE r.id = ? AND gs.global_session_id = ?"
                ,mapper, roomId, globalSessionId);
    }
}

