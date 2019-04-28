package com.gmail.eugene.shchemelyov.itemshop.service.impl;

import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.AppUserPrincipal;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.USER_NOT_FOUND_MESSAGE;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO userDTO = userService.loadUserByUsername(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
        return new AppUserPrincipal(userDTO);
    }
}
