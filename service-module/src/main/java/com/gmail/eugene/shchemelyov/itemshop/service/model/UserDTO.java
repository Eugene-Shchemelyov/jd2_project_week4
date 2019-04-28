package com.gmail.eugene.shchemelyov.itemshop.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.gmail.eugene.shchemelyov.itemshop.service.constant.UserDTOConstant.MAX_PASSWORD_SIZE;
import static com.gmail.eugene.shchemelyov.itemshop.service.constant.UserDTOConstant.MAX_USERNAME_SIZE;

public class UserDTO {
    private Long id;
    @NotNull
    @Size(max = MAX_USERNAME_SIZE)
    private String username;
    @NotNull
    @Size(max = MAX_PASSWORD_SIZE)
    private String password;
    @NotNull
    private String roleName;
    private Boolean isDeleted;

    public UserDTO(Long id,
                   String username,
                   String password,
                   String roleName,
                   Boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
