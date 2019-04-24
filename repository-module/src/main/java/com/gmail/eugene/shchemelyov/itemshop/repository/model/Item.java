package com.gmail.eugene.shchemelyov.itemshop.repository.model;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.enums.ItemStatusEnum;

public class Item {
    private Long id;
    private String name;
    private ItemStatusEnum status;
    private Boolean isDeleted;

    public Item(Long id,
                String name,
                ItemStatusEnum status,
                Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
