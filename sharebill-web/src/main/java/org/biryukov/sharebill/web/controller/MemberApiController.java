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
public class MemberApiController {

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




    @GetMapping("/api/{roomId}/members/find_by_global_session")
    @ResponseBody
    public List<org.biryukov.sharebill.service.jdbcrepo.pojo.Person> findMembersByGlobalSession(@PathVariable UUID roomId, HttpServletRequest request) {
        if (request.getCookies() != null) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("GLOBAL_ID")).collect(Collectors.toList());
            if (cookies.size() > 0) {
                UUID globalSessionId = UUID.fromString(cookies.get(0).getValue());
                List<org.biryukov.sharebill.service.jdbcrepo.pojo.Person> members = personJdbcRepository.findPersonsByGlobalSession(roomId, globalSessionId);

                return members;
            }
        }
        return Collections.emptyList();
    }


    @PostMapping("/api/room/{roomId}/member/add")
    @ResponseBody
    public Person user(@PathVariable UUID roomId, @RequestBody Person user, HttpServletRequest request) {
        user.setId(UUID.randomUUID());

        Room r = roomJpaRepository.findById(roomId).get();
        List<Cookie> cookies = Arrays.stream(request.getCookies())
                .filter(cookie -> "GLOBAL_ID".equals(cookie.getName()))
                .collect(Collectors.toList());

        org.biryukov.sharebill.service.jparepo.entity.Person p = new org.biryukov.sharebill.service.jparepo.entity.Person();
        p.setId(user.getId());
        p.setName(user.getName());
        p.setRoom(r);
        if (!cookies.isEmpty()) {
            UUID globalSessionId = UUID.fromString(cookies.get(0).getValue());
            GlobalSession globalSession = globalSessionJpaRepository.findByGlobalSessionId(globalSessionId);
            p.setGlobalSession(globalSession);
        }
        personJpaRepository.save(p);
        messagingTemplate.convertAndSend("/topic/" + roomId + "/members", personJpaRepository.findByRoom(roomId));
        return user;
    }


    @GetMapping("/api/room/{roomId}/members")
    @ResponseBody
    public List<org.biryukov.sharebill.service.jdbcrepo.pojo.Person> members(@PathVariable UUID roomId) {
        List<org.biryukov.sharebill.service.jdbcrepo.pojo.Person> persons = personJdbcRepository.findPerson(roomId);
        return persons;
    }


}
