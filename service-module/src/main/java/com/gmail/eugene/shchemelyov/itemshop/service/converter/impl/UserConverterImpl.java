package com.gmail.eugene.shchemelyov.itemshop.service.converter.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.UserConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    @Override
    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getName(),
                user.isDeleted()
        );
    }

    @Override
    public User toUser(UserDTO userDTO, Role role) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                role,
                userDTO.getPassword(),
                userDTO.isDeleted()
        );
    }
}
