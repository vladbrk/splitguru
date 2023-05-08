package org.biryukov.sharebill.service.jparepo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class GlobalSession {

    @Id
    private UUID id;
    private UUID globalSessionId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGlobalSessionId() {
        return globalSessionId;
    }

    public void setGlobalSessionId(UUID globalSessionId) {
        this.globalSessionId = globalSessionId;
    }
}
