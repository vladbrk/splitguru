package org.biryukov.sharebill.service.jparepo.entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    private UUID id;
    private String name;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "payer",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> payers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "consumer",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> consumers;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Person> getPayers() {
        return payers;
    }

    public void setPayers(List<Person> payers) {
        this.payers = payers;
    }

    public List<Person> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Person> consumers) {
        this.consumers = consumers;
    }
}
