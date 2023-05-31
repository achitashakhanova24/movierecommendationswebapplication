package com.techelevator.dao;

import com.techelevator.model.Account;

import java.util.List;

public interface AccountDao {
    List<String> getFavoritesById(int id);
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    Account getAccountByUsername(String username);
    Account updateAccount(int accountId, String email, List<String> favorites);
}
