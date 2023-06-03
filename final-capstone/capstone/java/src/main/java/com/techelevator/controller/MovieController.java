package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.model.Movie;
import com.techelevator.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/movie")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
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

    @GetMapping("/newReleases")
    public ResponseEntity<List<Movie>> newReleases(){

        return new ResponseEntity<>(movieService.displayLatestMovies(), HttpStatus.OK) ;
    }
}
