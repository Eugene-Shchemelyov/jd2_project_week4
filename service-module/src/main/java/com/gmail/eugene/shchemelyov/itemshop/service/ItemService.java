package com.gmail.eugene.shchemelyov.itemshop.service;

import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> getItems();

    ItemDTO add(ItemDTO itemDTO);
}
