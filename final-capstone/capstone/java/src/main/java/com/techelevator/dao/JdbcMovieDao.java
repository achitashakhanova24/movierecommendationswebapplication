package com.techelevator.dao;

import com.techelevator.model.Movie;
import com.techelevator.services.MovieService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Component
public class JdbcMovieDao implements MovieDao{



    private JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie getRandomMovie() {
        return null;

    }

    @Override
    public List<Movie> getFavorites() {
        return null;
    }


}
