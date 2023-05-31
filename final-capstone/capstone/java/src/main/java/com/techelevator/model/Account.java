package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountId;
    private int userId;
    private String email;
    private List<String> favorites = new ArrayList<>();

    public Account(){}

    public Account(int accountId, int userId, List<String> favorites, String email) {
        this.accountId = accountId;
        this.userId = userId;
        this.favorites = favorites;
        this.email = email;
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

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }


}
