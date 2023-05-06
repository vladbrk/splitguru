package org.biryukov.sharebill.web.controller.pojo;

import java.util.List;
import java.util.UUID;


public class Product {

    private UUID id;
    private String name;
    private Integer price;

    private List<Person> payers;
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
