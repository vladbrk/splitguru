package org.biryukov.sharebill.service.service;

import org.biryukov.sharebill.service.service.pojo.Settlement;

import java.util.UUID;

public interface SettlmentService {

    Settlement calculate(UUID roomId);
}
