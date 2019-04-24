package com.gmail.eugene.shchemelyov.itemshop.repository.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.ConnectionRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.DatabaseException;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.NotFoundException;
import com.gmail.eugene.shchemelyov.itemshop.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.CLASS_NOT_FOUND_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.CONNECTION_LOST_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.FILE_NOT_FOUND_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.TRANSACTION_FAILED_MESSAGE;

@Component
public class ConnectionRepositoryImpl implements ConnectionRepository {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionRepositoryImpl.class);
    private final DatabaseProperties databaseProperties;

    public ConnectionRepositoryImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        try {
            Class.forName(databaseProperties.getDatabaseDriverName());
        } catch (ClassNotFoundException e) {
            logger.error("{} {}", CLASS_NOT_FOUND_MESSAGE, e.getMessage(), e);
            throw new NotFoundException(String.format("%s %s", CLASS_NOT_FOUND_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", databaseProperties.getDatabaseUsername());
            properties.setProperty("password", databaseProperties.getDatabasePassword());
            return DriverManager.getConnection(databaseProperties.getDatabaseURL(), properties);
        } catch (SQLException e) {
            logger.error("{} {}", CONNECTION_LOST_MESSAGE, e.getMessage(), e);
            throw new DatabaseException(String.format("%s %s", CONNECTION_LOST_MESSAGE, e.getMessage()), e);
        }
    }

    @PostConstruct
    public void initializeDatabase() {
        try (Connection connection = getConnection()) {
            processSQL(connection);
        } catch (SQLException e) {
            logger.error("{} {}", CONNECTION_LOST_MESSAGE, e.getMessage());
            throw new DatabaseException(String.format(("%s %s"), CONNECTION_LOST_MESSAGE, e.getMessage()), e);
        }
    }

    private void processSQL(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            String tablesFileName = this.getClass().getResource(databaseProperties.getDatabaseInitialFile()).getPath();
            List<String> listQueries = new ArrayList<>();
            readFileQueries(tablesFileName, listQueries);
            for (String query : listQueries) {
                statement.addBatch(query);
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            logger.error("{} {}", TRANSACTION_FAILED_MESSAGE, e.getMessage());
            throw new DatabaseException(String.format(("%s %s"), TRANSACTION_FAILED_MESSAGE, e.getMessage()), e);
        }
    }

    private void readFileQueries(String fileName, List<String> listQueries) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                listQueries.add(line);
            }
        } catch (IOException e) {
            logger.error("{} {}", FILE_NOT_FOUND_MESSAGE, e.getMessage(), e);
            throw new NotFoundException(String.format("%s %s", FILE_NOT_FOUND_MESSAGE, e.getMessage()), e);
        }
    }
}
