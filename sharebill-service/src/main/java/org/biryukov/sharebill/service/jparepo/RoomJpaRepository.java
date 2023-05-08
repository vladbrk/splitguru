package org.biryukov.sharebill.service.jparepo;

import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomJpaRepository extends CrudRepository<Room, UUID> {

}
