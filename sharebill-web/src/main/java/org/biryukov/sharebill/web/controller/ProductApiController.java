package org.biryukov.sharebill.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.biryukov.sharebill.service.jdbcrepo.PersonJdbcRepository;
import org.biryukov.sharebill.service.jdbcrepo.RoomJdbcRepository;
import org.biryukov.sharebill.service.jparepo.GlobalSessionJpaRepository;
import org.biryukov.sharebill.service.jparepo.PersonJpaRepository;
import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.biryukov.sharebill.service.jparepo.RoomJpaRepository;
import org.biryukov.sharebill.service.jparepo.entity.GlobalSession;
import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.biryukov.sharebill.service.service.SettlmentService;
import org.biryukov.sharebill.web.controller.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ProductApiController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RoomJdbcRepository roomJdbcRepository;

    @Autowired
    private PersonJdbcRepository personJdbcRepository;

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private GlobalSessionJpaRepository globalSessionJpaRepository;

    @Autowired
    private SettlmentService settlmentService;


    @GetMapping("/api/room/{room}/products")
    @ResponseBody
    public List<org.biryukov.sharebill.service.jparepo.entity.Product> products(@PathVariable String room) {
        return productJpaRepository.findByRoom(room);
    }

    @GetMapping("/api/room/{room}/settlement/calculate")
    @ResponseBody
    public org.biryukov.sharebill.service.service.pojo.Settlement calculateSettlement(@PathVariable String room) {
        return settlmentService.calculate(room);
    }

}
