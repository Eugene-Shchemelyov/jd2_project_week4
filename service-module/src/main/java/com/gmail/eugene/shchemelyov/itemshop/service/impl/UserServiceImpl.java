package com.gmail.eugene.shchemelyov.itemshop.service.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.RoleRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.UserRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;
import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.UserConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.exception.ServiceException;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.SERVICE_ERROR_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.TRANSACTION_FAILED_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ItemConstant.IS_DELETED;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private UserConverter userConverter;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getUsers(connection);
                List<UserDTO> usersDTO = users.stream()
                        .map(user -> getUserDTO(connection, user))
                        .collect(Collectors.toList());
                connection.commit();
                return usersDTO;
            } catch (Exception e) {
                connection.rollback();
                logger.error("{} {}", TRANSACTION_FAILED_MESSAGE, e.getMessage());
                throw new ServiceException(String.format(("%s %s"), TRANSACTION_FAILED_MESSAGE, e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error("{} {}", SERVICE_ERROR_MESSAGE, e.getMessage());
            throw new ServiceException(String.format(("%s %s"), SERVICE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public void add(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = getUser(connection, userDTO);
                userRepository.add(connection, user);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                logger.error("{} {}", TRANSACTION_FAILED_MESSAGE, e.getMessage());
                throw new ServiceException(String.format(("%s %s"), TRANSACTION_FAILED_MESSAGE, e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error("{} {}", SERVICE_ERROR_MESSAGE, e.getMessage());
            throw new ServiceException(String.format(("%s %s"), SERVICE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public UserDTO loadUserByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = loadUser(connection, username);
                UserDTO userDTO = userConverter.toUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (Exception e) {
                connection.rollback();
                logger.error("{} {}", TRANSACTION_FAILED_MESSAGE, e.getMessage());
                throw new ServiceException(String.format(("%s %s"), TRANSACTION_FAILED_MESSAGE, e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error("{} {}", SERVICE_ERROR_MESSAGE, e.getMessage());
            throw new ServiceException(String.format(("%s %s"), SERVICE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    private User loadUser(Connection connection, String username) {
        User user = new User(null, username, null, null, null);
        User loadedUser = userRepository.loadUserByUsername(connection, user);
        Role role = roleRepository.getRoleById(connection, loadedUser.getRole());
        loadedUser.setRole(role);
        return loadedUser;
    }

    private User getUser(Connection connection, UserDTO userDTO) {
        Role role = new Role();
        role.setName(userDTO.getRoleName());
        role = roleRepository.getRoleByName(connection, role);
        User user = userConverter.toUser(userDTO, role);
        user.setDeleted(IS_DELETED);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    private UserDTO getUserDTO(Connection connection, User user) {
        Role role = roleRepository.getRoleById(connection, user.getRole());
        user.setRole(role);
        return userConverter.toUserDTO(user);
    }
}
