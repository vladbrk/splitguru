package org.biryukov.sharebill.web.utils;

import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.biryukov.sharebill.web.controller.pojo.Person;
import org.biryukov.sharebill.web.controller.pojo.Product;

import java.util.ArrayList;
import java.util.UUID;

public class Converter {

    public static org.biryukov.sharebill.service.jparepo.entity.Person convertToEntitytPerson(Person person, Room room) {
        org.biryukov.sharebill.service.jparepo.entity.Person personEntity = new org.biryukov.sharebill.service.jparepo.entity.Person();
        personEntity.setId(person.getId());
        personEntity.setName(person.getName());
        personEntity.setRoom(room);
        return personEntity;
    }

    public static org.biryukov.sharebill.service.jparepo.entity.Product convertToEntityProduct(Product product, Room room) {
        return convertToEntityProduct(product, product.getId(), room);
    }

    public static org.biryukov.sharebill.service.jparepo.entity.Product convertToEntityProduct(Product product, UUID id, Room room) {
        org.biryukov.sharebill.service.jparepo.entity.Product productEntityy = new org.biryukov.sharebill.service.jparepo.entity.Product();
        productEntityy.setId(id);
        productEntityy.setName(product.getName());
        productEntityy.setPrice(product.getPrice());
        productEntityy.setPayers(new ArrayList<>());
        productEntityy.setConsumers(new ArrayList<>());
        productEntityy.setRoom(room);
        product.getPayers().forEach((el) -> productEntityy.getPayers().add(convertToEntitytPerson(el, room)));
        product.getConsumers().forEach((el) -> productEntityy.getConsumers().add(convertToEntitytPerson(el, room)));
        return productEntityy;
    }
}
