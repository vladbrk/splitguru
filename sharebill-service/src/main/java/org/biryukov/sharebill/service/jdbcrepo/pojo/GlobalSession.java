package org.biryukov.sharebill.service.jdbcrepo.pojo;

import java.util.UUID;

public class GlobalSession {

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
