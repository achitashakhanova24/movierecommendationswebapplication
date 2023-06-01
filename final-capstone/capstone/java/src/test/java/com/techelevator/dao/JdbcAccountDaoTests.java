package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class JdbcAccountDaoTests extends BaseDaoTests{
//    protected static final Account ACCOUNT_1 = new Account(1, 1, new ArrayList<Integer>([1,2,3]));

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountByUserId(){
        Account actual = sut.getAccountByUserId(1);
        Assert.assertEquals(actual.getUserId(), 1);
//        Assert.assertEquals(actual.getFavorites().get(0), "1");
    }

    @Test
    public void updateAccount(){
       // Account actual = sut.updateAccount(1, "caakins123@gmail.com", new ArrayList<String>(Arrays.asList(new String[]{"0", "2", "3"})));
        //Assert.assertEquals(actual.getEmail(), "caakins123@gmail.com");
        //Assert.assertEquals(actual.getFavorites().get(0), "0");
    }

}
