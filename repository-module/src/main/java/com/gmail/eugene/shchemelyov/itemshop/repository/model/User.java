package com.gmail.eugene.shchemelyov.itemshop.repository.model;

public class User {
    private Long id;
    private String username;
    private Role role;
    private String password;
    private Boolean isDeleted;

    public User(Long id,
                String username,
                Role role,
                String password,
                Boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
