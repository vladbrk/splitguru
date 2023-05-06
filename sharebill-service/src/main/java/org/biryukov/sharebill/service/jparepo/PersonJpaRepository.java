package org.biryukov.sharebill.service.jparepo;

import org.biryukov.sharebill.service.jparepo.entity.Person;
import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PersonJpaRepository extends CrudRepository<Person, UUID> {

    @Query(value = "SELECT p FROM Person p JOIN p.room r WHERE r.room = :room")
    List<Person> findByRoom(@Param("room") String room);
}
