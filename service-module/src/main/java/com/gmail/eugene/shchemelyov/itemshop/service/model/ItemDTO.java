package com.gmail.eugene.shchemelyov.itemshop.service.model;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.enums.ItemStatusEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.gmail.eugene.shchemelyov.itemshop.service.constant.ItemDTOConstant.MAX_NAME_SIZE;

public class ItemDTO {
    private Long id;
    @NotNull
    @Size(max = MAX_NAME_SIZE)
    private String name;
    @NotNull
    private ItemStatusEnum status;
    private Boolean isDeleted;

    public ItemDTO(Long id,
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
