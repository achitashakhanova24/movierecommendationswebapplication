package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping
    public Account updateAccountInfo(@Valid @RequestBody AccountDto accountDto, Principal principal){
        return dao.updateAccount(accountDto, principal.getName());
    }
}
