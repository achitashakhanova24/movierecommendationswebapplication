package com.techelevator.dao;

import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;

import java.util.List;

public interface MovieDao {

    Movie getRandomMovie();
    MovieDto getMovie(int movieId, String username);

    List<MovieDto> getFavorites(String username);

    MovieDto favoriteMovie(int movieId, String username);

    MovieDto getFavoriteMovie(int movieId, String username);

    List<MovieDto> getWatchList(String username);

    MovieDto updateWatched(int movieId, String username);

    List<MovieDto> getSeenList(String username);

    List<MovieDto> updateRank(int movieId, int rank, String username);
    List<MovieDto> getRecommendations(String username);
}
