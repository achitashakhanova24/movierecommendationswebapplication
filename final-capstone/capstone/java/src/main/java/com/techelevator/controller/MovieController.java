package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;
import com.techelevator.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
    public Movie getRandomMovie(){
        return movieService.getRandomMovie();
    }

    @GetMapping(path="/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable int movieId){

        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK) ;
    }

    @GetMapping(path="/list/{page}")
    public ResponseEntity<List<MovieDto>> getPageOfMovies(@PathVariable int page){

        return new ResponseEntity<>(movieService.getPageOfMovies(page), HttpStatus.OK) ;
    }

    @GetMapping("/newReleases")
    public ResponseEntity<List<Movie>> newReleases(){

        return new ResponseEntity<>(movieService.displayLatestMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam(required = false) @DefaultValue("") String title, @RequestParam(required = false) @DefaultValue("") String genre, @RequestParam(required = false) @DefaultValue("") String releaseDate, @RequestParam(required = false) @DefaultValue("") String language){
        return new ResponseEntity<>(movieService.searchMovies(title, genre, releaseDate, language), HttpStatus.OK);
    }
}
