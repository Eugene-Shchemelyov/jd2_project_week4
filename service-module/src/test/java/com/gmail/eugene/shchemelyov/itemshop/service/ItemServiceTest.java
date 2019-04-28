package com.gmail.eugene.shchemelyov.itemshop.service;

import com.gmail.eugene.shchemelyov.itemshop.repository.ItemRepository;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;
import com.gmail.eugene.shchemelyov.itemshop.repository.model.enums.ItemStatusEnum;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.ItemConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.impl.ItemServiceImpl;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {
    private ItemService itemService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private Connection connection;
    @Mock
    private ItemConverter itemConverter;

    private Item item = new Item(1L, "ItemOne", ItemStatusEnum.READY, false);
    private ItemDTO itemDTO = new ItemDTO(1L, "ItemOne", ItemStatusEnum.READY, false);
    private List<Item> items = asList(item, item);
    private List<ItemDTO> itemsDTO = asList(itemDTO, itemDTO);

    @Before
    public void init() {
        when(itemRepository.getConnection()).thenReturn(connection);
        when(itemConverter.toItemDTO(item)).thenReturn(itemDTO);
        itemService = new ItemServiceImpl(itemRepository, itemConverter);
    }

    @Test
    public void shouldGetListItems() {
        when(itemRepository.getItems(connection)).thenReturn(items);
        List<ItemDTO> returnedItemsDTO = itemService.getItems();
        Assert.assertEquals(itemsDTO, returnedItemsDTO);
    }

    @Test
    public void shouldAddItemDTO() {
        when(itemRepository.add(connection, item)).thenReturn(item);
        when(itemConverter.toItem(itemDTO)).thenReturn(item);
        ItemDTO savedItemDTO = itemService.add(itemDTO);
        Assert.assertEquals(itemDTO, savedItemDTO);
    }
}
