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
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class RoomApiController {

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


    @PostMapping("/api/room/create")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (room.getRoom().isBlank()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        room.setId(UUID.randomUUID());
        room = roomJpaRepository.save(room);
        //roomJdbcRepository.createRoom(room);
        return new ResponseEntity(room, HttpStatus.OK);
    }

    @GetMapping("/api/room/check/{roomId}")
    @ResponseBody
    public Map<String, Boolean> checkRoom(@PathVariable UUID roomId, HttpServletRequest request) {
        boolean exists = roomJpaRepository.findById(roomId).isPresent();
        return Collections.singletonMap("success", exists);
    }

    @GetMapping("/api/room/find_by_global_session")
    @ResponseBody
    public List<org.biryukov.sharebill.service.jdbcrepo.pojo.Room> findRoomByGlobalSession(HttpServletRequest request) {

        if (request.getCookies() != null) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("GLOBAL_ID")).collect(Collectors.toList());
            if (cookies.size() > 0) {
                UUID globalSessionId = UUID.fromString(cookies.get(0).getValue());
                List<org.biryukov.sharebill.service.jdbcrepo.pojo.Room> rooms = roomJdbcRepository.findRoomByGlobalSession(globalSessionId);

                return rooms;
            }
        }
        return Collections.emptyList();
    }



}
