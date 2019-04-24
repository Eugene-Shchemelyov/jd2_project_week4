package com.gmail.eugene.shchemelyov.itemshop.service.converter.impl;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.Item;
import com.gmail.eugene.shchemelyov.itemshop.service.converter.ItemConverter;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemConverterImpl implements ItemConverter {
    @Override
    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getStatus(),
                item.isDeleted()
        );
    }

    @Override
    public Item toItem(ItemDTO itemDTO) {
        return new Item(
                itemDTO.getId(),
                itemDTO.getName(),
                itemDTO.getStatus(),
                itemDTO.isDeleted()
        );
    }
}
