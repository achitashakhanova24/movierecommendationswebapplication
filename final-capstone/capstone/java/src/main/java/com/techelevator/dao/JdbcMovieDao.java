package com.techelevator.dao;

import com.techelevator.model.Movie;
import com.techelevator.model.MovieDto;
import com.techelevator.services.MovieService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Component
public class JdbcMovieDao implements MovieDao{


    private MovieService movieService;
    private JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate, MovieService movieService) {
        this.jdbcTemplate = jdbcTemplate;
        this.movieService = movieService;
    }

    @Override
    public Movie getRandomMovie() {
        return null;

    }

    @Override
    public List<Movie> getFavorites() {
        return null;
    }

    @Override
    public MovieDto favoriteMovie(int movieId, String username) {
        String sql = "INSERT INTO favorites (user_id, movie_id, watched) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, ?) RETURNING favorites_id";
        try {
            MovieDto dto = getFavoriteMovie(movieId, username);
            if(dto != null){
                // IMPLEMENT DELETE FUNCTION
                return unfavoriteMovie(movieId, username);
            }
            int favoritesId = jdbcTemplate.queryForObject(sql, Integer.class, username, movieId, false);
            if(favoritesId != 0) {
                MovieDto movieDto = movieService.getMovie(movieId);
//                MovieDto dto = getFavoriteMovie(movieId, username);
                movieDto.setWatched(false);
                movieDto.setFavorited(true);
                return movieDto;
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
            throw new DataIntegrityViolationException(e.getMessage());
        }


    }

    @Override
    public MovieDto getFavoriteMovie(int movieId, String username) {
        String sql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "WHERE movie_id = ? AND users.username = ?";

        try{
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, movieId, username);
            if(rowSet.next()){
                return mapRowToMovieDto(rowSet);
            } else {
                return null;
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public MovieDto unfavoriteMovie(int movieId, String username) {
        String sql = "DELETE FROM favorites " +
                "WHERE movie_id = ? AND user_id = (SELECT user_id FROM users WHERE username = ?);";
        try{
            int rowsAffected = jdbcTemplate.update(sql, movieId, username);
            if(rowsAffected != 0) {
                MovieDto movieDto = movieService.getMovie(movieId);
                movieDto.setWatched(false);
                movieDto.setFavorited(false);
                return movieDto;
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    public MovieDto mapRowToMovieDto(SqlRowSet sqlRowSet){
        MovieDto movieDto = new MovieDto();
        movieDto.setFavorited(true);
        movieDto.setRank(sqlRowSet.getInt("rank"));
        movieDto.setWatched(sqlRowSet.getBoolean("watched"));
        movieDto.setMovieId(sqlRowSet.getInt("movie_id"));
        return movieDto;
    }

}
