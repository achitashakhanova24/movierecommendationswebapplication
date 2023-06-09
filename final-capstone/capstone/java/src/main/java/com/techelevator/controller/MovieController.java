package com.techelevator.controller;

import com.techelevator.dao.MovieDao;
import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;
import com.techelevator.model.MovieTableDto;
import com.techelevator.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
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
    public ResponseEntity<MovieDto> getMovie(@PathVariable int movieId, Principal principal){

        return new ResponseEntity<>(movieDao.getMovie(movieId, principal.getName()), HttpStatus.OK) ;
    }

    @GetMapping(path="/byTitle/{title}")
    public ResponseEntity<MovieDto> getMovieByTitle(@PathVariable String title){
        return new ResponseEntity<>(movieService.getMovieByTitle(title), HttpStatus.OK) ;
    }

    @GetMapping(path="/list")
    public ResponseEntity<List<MovieTableDto>> getPageOfMovies(){

        return new ResponseEntity<>(movieService.getPageOfMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/newReleases")
    public ResponseEntity<List<Movie>> newReleases(){

        return new ResponseEntity<>(movieService.displayLatestMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieTableDto>> searchMovies(@RequestParam(required = false) @DefaultValue("") String title, @RequestParam(required = false) @DefaultValue("") String genre, @RequestParam(required = false) @DefaultValue("") String releaseDate, @RequestParam(required = false) @DefaultValue("") String language){
        return new ResponseEntity<>(movieService.searchMovies(title, genre, releaseDate, language), HttpStatus.OK);
    }

    @PostMapping("/favorite/{movieId}")
    public ResponseEntity<MovieDto> favoriteMovie(@PathVariable int movieId, Principal principal){
        return new ResponseEntity<>(movieDao.favoriteMovie(movieId, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/myFavorites")
    public ResponseEntity<List <MovieDto>> favoriteList(Principal principal){
        return new ResponseEntity<>(movieDao.getFavorites(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/myWatchList")
    public ResponseEntity <List <MovieDto>> watchList(Principal principal){
        return new ResponseEntity<>(movieDao.getWatchList(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/editWatched/{movieId}")
    public ResponseEntity<MovieDto> editWatched(@PathVariable int movieId, Principal principal){
        return new ResponseEntity<>(movieDao.updateWatched(movieId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/mySeenList")
    public ResponseEntity <List <MovieDto>> seenList(Principal principal){
        return new ResponseEntity<>(movieDao.getSeenList(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/updateRank/{movieId}/{rank}")
    public ResponseEntity<List <MovieDto>> updateRank(@PathVariable int movieId, @PathVariable int rank, Principal principal){
        return new ResponseEntity<>(movieDao.updateRank(movieId, rank, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<MovieDto>> getRecommendations(Principal principal) {
        return new ResponseEntity<>(movieDao.getRecommendations(principal.getName()), HttpStatus.OK);
    }
}
