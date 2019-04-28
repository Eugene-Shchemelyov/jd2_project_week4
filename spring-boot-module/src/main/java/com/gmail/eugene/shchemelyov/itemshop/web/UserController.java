package com.gmail.eugene.shchemelyov.itemshop.web;

import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/private/users")
    public String findAllUsers(Model model) {
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        logger.debug("Get items method");
        return "users";
    }
}
