package com.gmail.eugene.shchemelyov.itemshop.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "com.gmail.eugene.shchemelyov.itemshop",
        exclude = UserDetailsServiceAutoConfiguration.class
)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
