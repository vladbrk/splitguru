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
public class ProductWsController {

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


    @MessageMapping("/{roomId}/addproduct")
    @SendTo("/topic/{roomId}/products")
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> addProduct(@DestinationVariable UUID roomId, @Payload Product product) {
        Room roomEntity = roomJpaRepository.findById(roomId).get();

        productJpaRepository.save(Converter.convertToEntityProduct(product, UUID.randomUUID(), roomEntity));

        org.biryukov.sharebill.service.service.pojo.Settlement settlement =  settlmentService.calculate(roomId);
        messagingTemplate.convertAndSend(String.format("/topic/%s/receipt/calculate", roomId), settlement);
        return productJpaRepository.findByRoom(roomId);
    }

    @MessageMapping("/{roomId}/product/update")
    @SendTo("/topic/{roomId}/products")
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> updateProduct(@DestinationVariable UUID roomId, @Payload Product product) {
        Room roomEntity = roomJpaRepository.findById(roomId).get();

        productJpaRepository.save(Converter.convertToEntityProduct(product, roomEntity));

        org.biryukov.sharebill.service.service.pojo.Settlement settlement =  settlmentService.calculate(roomId);
        messagingTemplate.convertAndSend(String.format("/topic/%s/receipt/calculate", roomId), settlement);

        return productJpaRepository.findByRoom(roomId);
    }




}
