package org.biryukov.sharebill.service.service;

import java.util.UUID;

public interface PinService {

    String generatePin(UUID roomId);
    UUID getRoomId(String pin);
}
