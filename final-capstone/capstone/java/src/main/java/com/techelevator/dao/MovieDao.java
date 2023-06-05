package com.techelevator.dao;

import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;

import java.util.List;

public interface MovieDao {

    Movie getRandomMovie();

    List<Movie> getFavorites();

    MovieDto favoriteMovie(int movieId, String username);

    MovieDto getFavoriteMovie(int movieId, String username);
}
