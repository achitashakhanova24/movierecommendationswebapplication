package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountId;
    private int userId;
    private String email;
    private String username;

    public Account(){}

    public Account(int accountId, int userId, String email) {
        this.accountId = accountId;
        this.userId = userId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
