package org.biryukov.sharebill.web.controller;

import org.biryukov.sharebill.service.jdbcrepo.PersonJdbcRepository;
import org.biryukov.sharebill.service.jparepo.PersonJpaRepository;
import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.biryukov.sharebill.service.jparepo.RoomJpaRepository;
import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.biryukov.sharebill.service.service.SettlmentService;
import org.biryukov.sharebill.web.controller.pojo.Product;
import org.biryukov.sharebill.web.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class WebSocketController {

    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PersonJdbcRepository personJdbcRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private SettlmentService settlmentService;


    @MessageMapping("/{room}/addproduct")
    @SendTo("/topic/{room}/products")
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> addProduct(@DestinationVariable String room, @Payload Product product) {
        Room roomEntity = roomJpaRepository.findByRoom(room);

        productJpaRepository.save(Converter.convertToEntityProduct(product, UUID.randomUUID(), roomEntity));

        org.biryukov.sharebill.service.service.pojo.Settlement settlement =  settlmentService.calculate(room);
        messagingTemplate.convertAndSend(String.format("/topic/%s/receipt/calculate", room), settlement);
        return productJpaRepository.findByRoom(room);
    }

    @MessageMapping("/{room}/product/update")
    @SendTo("/topic/{room}/products")
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> updateProduct(@DestinationVariable String room, @Payload Product product) {
        Room roomEntity = roomJpaRepository.findByRoom(room);

        productJpaRepository.save(Converter.convertToEntityProduct(product, roomEntity));

        org.biryukov.sharebill.service.service.pojo.Settlement settlement =  settlmentService.calculate(room);
        messagingTemplate.convertAndSend(String.format("/topic/%s/receipt/calculate", room), settlement);

        return productJpaRepository.findByRoom(room);
    }




}
