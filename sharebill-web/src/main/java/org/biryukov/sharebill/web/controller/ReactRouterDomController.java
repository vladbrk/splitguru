package org.biryukov.sharebill.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactRouterDomController {

    @GetMapping("/login")
    public String login() {
        return "/index.html";
    }

    @GetMapping("/bill/*")
    public String bill() {
        return "/index.html";
    }
}
