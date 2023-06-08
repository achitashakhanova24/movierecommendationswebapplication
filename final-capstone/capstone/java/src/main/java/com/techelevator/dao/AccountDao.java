package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;

import java.util.List;

public interface AccountDao {
    List<String> getFavoritesById(int id);
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    Account getAccountByUsername(String username);
    Account updateAccount(String email, String username);
}
