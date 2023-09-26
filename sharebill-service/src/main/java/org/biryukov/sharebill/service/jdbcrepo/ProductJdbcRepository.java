package org.biryukov.sharebill.service.jdbcrepo;

import org.biryukov.sharebill.service.jdbcrepo.pojo.Person;
import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPerson(String name, String room, List<Person> payers, List<Person> consumers) {
        UUID roomId = jdbcTemplate.queryForObject("select id from sharebill.room where room = ?", UUID.class, room);

        UUID productId = UUID.randomUUID();
        jdbcTemplate.update(
                "insert into sharebill.product(id, name, price) value(?, ?, ?)",
                productId,
                name,
                roomId);

        payers.forEach((p) -> {
            jdbcTemplate.update(
                    "insert into sharebill.payer(product_id, person_id) value(?, ?)",
                    productId,
                    p.getId());
        });

        consumers.forEach((p) -> {
            jdbcTemplate.update(
                    "insert into sharebill.consumers(product_id, person_id) value(?, ?)",
                    productId,
                    p.getId());
        });
    }

    public void updatePlainProduct(Product product) {
        jdbcTemplate.update(
                "update sharebill.product set name = ?, price = ? where id = ?",
                product.getName(),
                product.getPrice(),
                product.getId());
    }

    public UUID findRoomId(UUID productId) {
        return jdbcTemplate.queryForObject("select room_id from sharebill.product where id = ?", UUID.class, productId);
    }
}
