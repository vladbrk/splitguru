package org.biryukov.sharebill.web.service;

import org.biryukov.sharebill.service.jparepo.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimpBroadcastImpl implements SimpBroadcast {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public void castProdcuts(UUID roomId) {
        messagingTemplate.convertAndSend(String.format("/topic/%s/products", roomId), productJpaRepository.findByRoom(roomId));
    }
}
