package org.biryukov.sharebill.service.jdbcrepo.pojo;

import org.biryukov.sharebill.service.jdbcrepo.pojo.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId((UUID) rs.getObject("id"));
        person.setName(rs.getString("name"));
        return person;
    }
}