package org.biryukov.sharebill.service.jparepo;

import org.biryukov.sharebill.service.jparepo.entity.GlobalSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GlobalSessionJpaRepository extends CrudRepository<GlobalSession, UUID> {

    GlobalSession findByGlobalSessionId(UUID globalSessionId);
}
