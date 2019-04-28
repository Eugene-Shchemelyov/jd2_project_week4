package com.gmail.eugene.shchemelyov.itemshop.repository.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.RoleRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.DatabaseException;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.NotFoundException;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.DATABASE_ERROR_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.ROLE_NOT_FOUND_MESSAGE;

@Repository
public class RoleRepositoryImpl extends ConnectionRepositoryImpl implements RoleRepository {
    private static final Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Autowired
    public RoleRepositoryImpl(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public Role getRoleById(Connection connection, Role role) {
        String query = "SELECT * FROM T_ROLE WHERE F_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, role.getId());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    role.setName(resultSet.getString("F_NAME"));
                    return role;
                }
                logger.error(ROLE_NOT_FOUND_MESSAGE);
                throw new NotFoundException(ROLE_NOT_FOUND_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error("{} {}", DATABASE_ERROR_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public Role getRoleByName(Connection connection, Role role) {
        String query = "SELECT * FROM T_ROLE WHERE F_NAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, role.getName());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    role.setId(resultSet.getLong("F_ID"));
                    return role;
                }
                logger.error(ROLE_NOT_FOUND_MESSAGE);
                throw new NotFoundException(ROLE_NOT_FOUND_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error("{} {}", DATABASE_ERROR_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }
}
