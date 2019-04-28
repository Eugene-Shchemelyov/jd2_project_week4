package com.gmail.eugene.shchemelyov.itemshop.repository;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ConnectionRepository {
    List<User> getUsers(Connection connection);

    void add(Connection connection, User user);

    User loadUserByUsername(Connection connection, User user);
}
