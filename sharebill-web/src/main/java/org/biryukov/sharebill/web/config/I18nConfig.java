package org.biryukov.sharebill.web.config;

import org.biryukov.sharebill.web.controller.MessagesResourceBundle;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class I18nConfig {

    @Bean
    public MessagesResourceBundle messagesResourceBundle() {
        return new MessagesResourceBundle();
    }

}
