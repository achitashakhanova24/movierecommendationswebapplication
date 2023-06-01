package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.model.Movie;
import com.techelevator.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movie")
@PreAuthorize("isAuthenticated()")
public class MovieController {

    @Autowired
    MovieService movieService;

    private MovieDao movieDao;

    public MovieController(MovieDao movieDao){
        this.movieDao = movieDao;
    }

    @GetMapping
    public Movie getMovie(){
        return movieService.getMovie();
    }
}
