package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao dao;

    public AccountController(AccountDao dao) {this.dao = dao;}

    @GetMapping
    public Account getAccountInfo(Principal principal){
        return dao.getAccountByUsername(principal.getName());
    }
}
