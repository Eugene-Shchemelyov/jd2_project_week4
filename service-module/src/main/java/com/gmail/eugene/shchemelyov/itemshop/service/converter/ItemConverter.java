package com.gmail.eugene.shchemelyov.itemshop.service.converter;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;

public interface ItemConverter {
    ItemDTO toItemDTO(Item item);

    Item toItem(ItemDTO itemDTO);
}
