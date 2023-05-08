package org.biryukov.sharebill.service.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PinServiceImpl implements PinService {

    private final ConcurrentHashMap<String, UUID> pins = new ConcurrentHashMap<>();

    private Random r = new Random();

    @Override
    public String generatePin(UUID roomId) {


        String pin = "";

        int ATTEMPTS = 10;
        int STRONG_THRESHOLD = 5;
        for (int attempt = 0; attempt < ATTEMPTS; attempt++) {
            if (attempt < STRONG_THRESHOLD) {
                pin = genPin();
            } else {
                pin = genStrongPin();
            }
            if (!pins.containsKey(pin)) {
                synchronized (pins) {
                    if (!pins.containsKey(pin)) {
                        pins.put(pin, roomId);
                    }
                }
            }
            if (attempt == ATTEMPTS - 1) {
                throw new RuntimeException("Can't generate pin code");
            }
        }
        return pin;
    }

    private String genStrongPin() {
        return Integer.toString(r.nextInt(1001, 9999));
    }
    private String genPin() {

        String pin = "";

        int f1 = r.nextInt(1, 9);
        int f2 = r.nextInt(0, 8);

        switch (r.nextInt(0, 8)) {
            case 0:
                pin = toString(f1, f2, f1, f2);
                break;
            case 1:
                pin = toString(f1, f2, f1, f2 + 1);
                break;
            case 2:
                pin = toString(f1, f1, f2, f2);
                break;
            case 3:
                pin = toString(f1, f1, f2, f2 + 1);
                break;
            case 4:
                int f3 = r.nextInt(1, 5);
                pin = toString(f3, f3 + 2, f3 + 4, f3 + 4);
                break;
            case 5:
                int f4 = r.nextInt(1, 3);
                pin = toString(f4, f4 + 3, f4 + 6, f4 + 6);
                break;
            case 6:
                pin = toString(r.nextInt(1, 9),
                        r.nextInt(0, 1) * 5,
                        r.nextInt(1, 9),
                        r.nextInt(0, 1) * 5);
                break;
            case 7:
                pin = toString(r.nextInt(1, 3) * 3,
                        r.nextInt(0, 3) * 3,
                        r.nextInt(1, 3) * 3,
                        r.nextInt(0, 3) * 5);
                break;
            case 8:
                pin = toString(r.nextInt(1, 4) * 2,
                        r.nextInt(0, 4) * 2,
                        r.nextInt(1, 4) * 2,
                        r.nextInt(0, 4) * 2);
                break;
        }
        return pin;
    }

    @Override
    public UUID getRoomId(String pin) {
        return null;
    }

    private String toString(int f1, int f2, int f3, int f4) {
        return Integer.toString(f1) + f2 + f3 + f4;
    }


}
