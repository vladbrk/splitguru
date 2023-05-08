package org.biryukov.sharebill.service.jdbcrepo.pojo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

public class Room {

    private UUID id;
    private String room;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
