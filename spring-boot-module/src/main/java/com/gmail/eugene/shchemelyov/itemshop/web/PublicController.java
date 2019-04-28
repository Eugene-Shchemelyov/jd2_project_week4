package com.gmail.eugene.shchemelyov.itemshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublicController {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String authorisation() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
