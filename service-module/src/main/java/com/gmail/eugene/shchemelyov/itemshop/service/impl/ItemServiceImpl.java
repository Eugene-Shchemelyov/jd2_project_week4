package com.gmail.eugene.shchemelyov.itemshop.service.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.ItemRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;
import com.gmail.eugene.shchemelyov.itemshop.service.ItemService;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.ItemConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.exception.ServiceException;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.ADD_ITEM;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.SERVICE_ERROR_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.TRANSACTION_FAILED_MESSAGE;
import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ItemConstant.IS_DELETED;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public List<ItemDTO> getItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.getItems(connection);
                List<ItemDTO> itemsDTO = items.stream()
                        .map(itemConverter::toItemDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return itemsDTO;
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
    public ItemDTO add(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConverter.toItem(itemDTO);
                item.setDeleted(IS_DELETED);
                Item savedItem = itemRepository.add(connection, item);
                connection.commit();
                return itemConverter.toItemDTO(savedItem);
            } catch (Exception e) {
                connection.rollback();
                logger.error("{} {} {} {}", TRANSACTION_FAILED_MESSAGE, ADD_ITEM, itemDTO.getName(), e.getMessage());
                throw new ServiceException(String.format(("%s %s"), TRANSACTION_FAILED_MESSAGE, e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error("{} {}", SERVICE_ERROR_MESSAGE, e.getMessage());
            throw new ServiceException(String.format(
                    ("%s %s %s %s"), SERVICE_ERROR_MESSAGE, ADD_ITEM, itemDTO.getName(), e.getMessage()), e);
        }
    }
}
