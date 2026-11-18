package com.demo.shop.model;

import java.util.Date;

public class User {

    private long id;
    private String name;
    private String email;
    private Date lastLogin;
    private boolean active;

    public User(long id, String name, String email, Date lastLogin, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLogin = lastLogin;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
