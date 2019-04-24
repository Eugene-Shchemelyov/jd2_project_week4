package com.gmail.eugene.shchemelyov.itemshop.repository.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.ItemRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.exception.DatabaseException;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.enums.ItemStatusEnum;
import com.gmail.eugene.shchemelyov.itemshop.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.DATABASE_ERROR_MESSAGE;

@Repository
public class ItemRepositoryImpl extends ConnectionRepositoryImpl implements ItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Autowired
    public ItemRepositoryImpl(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public List<Item> getItems(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM T_ITEM";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(getItem(resultSet));
                }
                return items;
            }
        } catch (SQLException e) {
            logger.error(DATABASE_ERROR_MESSAGE);
            throw new DatabaseException(String.format("%s %s", DATABASE_ERROR_MESSAGE, e.getMessage()), e);
        }
    }

    @Override
    public Item add(Connection connection, Item item) {
        return null;
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong("F_ID"),
                resultSet.getString("F_NAME"),
                ItemStatusEnum.valueOf(resultSet.getString("F_STATUS")),
                resultSet.getBoolean("F_DELETED")
        );
    }
}
