package com.gmail.eugene.shchemelyov.itemshop.service;

import com.gmail.eugene.shchemelyov.itemshop.repository.RoleRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.UserRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.UserConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.impl.UserServiceImpl;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private Connection connection;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user = new User(1L, "username", new Role(), "password", false);
    private UserDTO userDTO = new UserDTO(1L, "username", "password", "CUSTOMER", false);
    private List<UserDTO> usersDTO = asList(userDTO, userDTO);
    private List<User> users = asList(user, user);

    @Before
    public void init() {
        when(userRepository.getConnection()).thenReturn(connection);
        when(userConverter.toUserDTO(user)).thenReturn(userDTO);
        userService = new UserServiceImpl(userRepository, userConverter, roleRepository, passwordEncoder);
    }

    @Test
    public void shouldGetListUsers() {
        when(userRepository.getUsers(connection)).thenReturn(users);
        List<UserDTO> loadedUsers = userService.getUsers();
        Assert.assertEquals(usersDTO, loadedUsers);
    }
}
