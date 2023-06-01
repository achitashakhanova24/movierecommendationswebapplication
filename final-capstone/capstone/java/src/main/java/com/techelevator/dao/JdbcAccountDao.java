package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getFavoritesById(int id) {
        return null;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            if(rowSet.next()) {
                Account acc = mapRowToAccount(rowSet);
                return acc;
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }
        catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, accountId);
            if(rowSet.next()) {
                Account acc = mapRowToAccount(rowSet);
                return acc;
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }
        catch (EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Account getAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts " +
                "JOIN users ON users.user_id = accounts.user_id " +
                "WHERE users.username = ?;";
        try {
            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, username);
            if(rowset.next()) {
                return mapRowToAccount(rowset);
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        catch(CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public Account updateAccount(AccountDto accountDto, String username) {
        String sql = "UPDATE accounts SET email = ?, favorites = ? " +
                "FROM users " +
                "WHERE users.user_id = accounts.user_id AND users.username = ?";
        try {
            jdbcTemplate.update(sql, accountDto.getEmail(),accountDto.getFavorites().toArray(new String [accountDto.getFavorites().size()]), username);

            return getAccountByUsername(username);
        }
        catch(CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Invalid operation - Data integrity error");
        }
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        /*String[] favs = rowSet.getString("favorites").split(",");
        List<String> favsList = Arrays.asList(favs);*/
        Object favsArray = rowSet.getObject("favorites");
        String[] objectArr = (String[])favsArray;
        return new Account(rowSet.getInt("account_id"), rowSet.getInt("user_id"), Arrays.asList(objectArr), rowSet.getString("email"));

    }
}
