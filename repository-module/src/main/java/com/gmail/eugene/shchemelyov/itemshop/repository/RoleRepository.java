package com.gmail.eugene.shchemelyov.itemshop.repository;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;

import java.sql.Connection;

public interface RoleRepository extends ConnectionRepository {
    Role getRoleById(Connection connection, Role role);

    Role getRoleByName(Connection connection, Role role);
}
