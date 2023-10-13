package org.biryukov.sharebill.web;


import org.biryukov.sharebill.service.config.JpaConfig;
import org.biryukov.sharebill.web.config.I18nConfig;
import org.biryukov.sharebill.web.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan("org.biryukov.sharebill.web")
@Import({WebSocketConfig.class, JpaConfig.class, I18nConfig.class})
public class ShareBillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareBillApplication.class, args);
    }
}
