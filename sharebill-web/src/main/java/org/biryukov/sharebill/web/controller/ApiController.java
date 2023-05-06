package org.biryukov.sharebill.web.controller;

import org.biryukov.sharebill.service.jdbcrepo.PersonJdbcRepository;
import org.biryukov.sharebill.service.jdbcrepo.RoomJdbcRepository;
import org.biryukov.sharebill.service.jparepo.PersonJpaRepository;
import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.biryukov.sharebill.service.jparepo.RoomJpaRepository;
import org.biryukov.sharebill.service.jparepo.entity.Room;
import org.biryukov.sharebill.service.service.SettlmentService;
import org.biryukov.sharebill.web.controller.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class ApiController {

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
    private SettlmentService settlmentService;


    @GetMapping("/api/room/create/{room}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> createRoom(@PathVariable String room) {
        if (roomJpaRepository.findByRoom(room) == null) {
            Room r = new Room();
            r.setId(UUID.randomUUID());
            r.setRoom(room);
            //roomJpaRepository.save(r);
            roomJdbcRepository.createRoom(room);
            return Collections.singletonMap("success", Boolean.TRUE);
        }
        return Collections.singletonMap("success", Boolean.FALSE);
    }

    @GetMapping("/api/room/check/{room}")
    @ResponseBody
    public Map<String, Boolean> checkRoom(@PathVariable String room) {
        boolean exists = roomJpaRepository.findByRoom(room) != null;
        return Collections.singletonMap("success", exists);
    }


    @PostMapping("/api/room/{room}/member/add")
    @ResponseBody
    public Person user(@PathVariable String room, @RequestBody Person user) {
        user.setId(UUID.randomUUID());

        Room r = roomJpaRepository.findByRoom(room);
        org.biryukov.sharebill.service.jparepo.entity.Person p = new org.biryukov.sharebill.service.jparepo.entity.Person();
        p.setId(user.getId());
        p.setName(user.getName());
        p.setRoom(r);
        personJpaRepository.save(p);
        messagingTemplate.convertAndSend("/topic/" + room + "/members", personJpaRepository.findByRoom(room));
        return user;
    }


    @GetMapping("/api/room/{room}/members")
    @ResponseBody
    public List<org.biryukov.sharebill.service.jdbcrepo.Person> members(@PathVariable String room) {
        List<org.biryukov.sharebill.service.jdbcrepo.Person> persons = personJdbcRepository.findPerson(room);
        return persons;
    }

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
