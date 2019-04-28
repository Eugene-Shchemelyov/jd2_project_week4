package com.gmail.eugene.shchemelyov.itemshop.repository.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.UserRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.DatabaseException;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.NotFoundException;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Role;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.User;
import com.gmail.eugene.shchemelyov.itemshop.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.DATABASE_ERROR_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.ROLE_NOT_FOUND_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.USER_NOT_FOUND_MESSAGE;

@Repository
public class UserRepositoryImpl extends ConnectionRepositoryImpl implements UserRepository {
    private final static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public UserRepositoryImpl(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public List<User> getUsers(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM T_USER";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(getUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error("{} {}", DATABASE_ERROR_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public void add(Connection connection, User user) {
        String query = "INSERT INTO T_USER (F_USERNAME, F_PASSWORD, F_ROLE_ID, F_DELETED) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getRole().getId());
            ps.setBoolean(4, user.isDeleted());
            int countUserAdded = ps.executeUpdate();
            logger.info("Count users added: {}", countUserAdded);
        } catch (SQLException e) {
            logger.error("{} {}", DATABASE_ERROR_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public User loadUserByUsername(Connection connection, User user) {
        String query = "SELECT * FROM T_USER WHERE F_USERNAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
                logger.error(USER_NOT_FOUND_MESSAGE);
                throw new NotFoundException(USER_NOT_FOUND_MESSAGE);
            }
        } catch (SQLException e) {
            logger.error("{} {}", DATABASE_ERROR_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("F_ID"),
                resultSet.getString("F_USERNAME"),
                getRole(resultSet.getLong("F_ROLE_ID")),
                resultSet.getString("F_PASSWORD"),
                resultSet.getBoolean("F_DELETED")
        );
    }

    private Role getRole(Long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
