package org.biryukov.sharebill.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
public class I18nController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessagesResourceBundle messagesResourceBundle;

    @GetMapping("/api/lang")
    public Map<String, String> lang(@PathParam("locale") String locale, HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json; charset=utf-8");
        return messagesResourceBundle.getAllMessages().get(new Locale(locale));
    }

}
