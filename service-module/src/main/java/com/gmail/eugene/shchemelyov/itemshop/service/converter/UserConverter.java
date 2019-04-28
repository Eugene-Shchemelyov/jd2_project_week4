package com.gmail.eugene.shchemelyov.itemshop.service.converter;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;

public interface UserConverter {
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO, Role role);
}
