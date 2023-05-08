package org.biryukov.sharebill.service.jparepo;

import org.biryukov.sharebill.service.jparepo.entity.Product;
import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends CrudRepository<Product, UUID> {

    @Query(value = "SELECT p FROM Product p JOIN p.room r WHERE r.id = :roomId")
    List<Product> findByRoom(@Param("roomId") UUID roomId);
}
