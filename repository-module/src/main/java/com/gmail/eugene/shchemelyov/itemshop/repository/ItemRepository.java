package com.gmail.eugene.shchemelyov.itemshop.repository;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository extends ConnectionRepository{
    List<Item> getItems(Connection connection);

    Item add(Connection connection, Item item);
}
