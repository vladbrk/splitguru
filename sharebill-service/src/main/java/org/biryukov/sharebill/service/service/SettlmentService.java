package org.biryukov.sharebill.service.service;

import org.biryukov.sharebill.service.service.pojo.Settlement;

public interface SettlmentService {

    Settlement calculate(String room);
}
