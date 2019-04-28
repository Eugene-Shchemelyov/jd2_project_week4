package com.gmail.eugene.shchemelyov.itemshop.service;

import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    void add(UserDTO userDTO);

    UserDTO loadUserByUsername(String username);
}
