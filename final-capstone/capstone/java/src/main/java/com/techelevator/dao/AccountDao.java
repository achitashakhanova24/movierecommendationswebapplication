package com.techelevator.dao;

import com.techelevator.model.Account;

import java.util.List;

public interface AccountDao {
    List<String> getFavoritesById(int id);
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    Account updateAccount(int accountId, String email, List<String> favorites);
}
