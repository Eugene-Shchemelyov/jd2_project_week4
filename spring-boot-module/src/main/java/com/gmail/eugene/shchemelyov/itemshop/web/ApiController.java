package com.gmail.eugene.shchemelyov.itemshop.web;

import com.gmail.eugene.shchemelyov.itemshop.service.ItemService;
import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.gmail.eugene.shchemelyov.itemshop.service.constant.RoleConstant.ADMINISTRATOR;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public ApiController(UserService userService,
                         ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @Secured(value = {ADMINISTRATOR})
    @PutMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<HttpStatus> saveUserDTO(@Valid @RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured(value = {ADMINISTRATOR})
    @PutMapping(value = "/item", consumes = "application/json")
    public ResponseEntity<HttpStatus> saveItemDTO(@Valid @RequestBody ItemDTO itemDTO) {
        itemService.add(itemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
