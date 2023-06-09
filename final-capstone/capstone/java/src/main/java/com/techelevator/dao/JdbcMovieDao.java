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
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
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
    public MovieDto getMovie(int movieId, String username) {
        MovieDto dto = getFavoriteMovie(movieId, username);
        MovieDto movie = new MovieDto();
        if(dto == null) {
            movie = movieService.getMovie(movieId);
            movie.setFavorited(false);
            movie.setWatched(false);
            movie.setRank(0);
        }
        else {
            movie = movieService.getMovie(movieId);
            movie.setWatched(dto.isWatched());
            movie.setFavorited(true);
            movie.setRank(dto.getRank());
        }
        return movie;
    }

    @Override
    public MovieDto favoriteMovie(int movieId, String username) {
        String sql = "INSERT INTO favorites (user_id, movie_id, watched, rank) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, false, ?) RETURNING favorites_id";
        try {
            MovieDto dto = getFavoriteMovie(movieId, username);
            if(dto != null){
                return unfavoriteMovie(movieId, username);
            }
            int totalFavorites = getFavorites(username).size();
            int favoritesId = 0;
            int rank = 0;
            if(totalFavorites < 10) {
                rank = totalFavorites + 1;
                favoritesId = jdbcTemplate.queryForObject(sql, Integer.class, username, movieId, rank);
            } else  {
            favoritesId = jdbcTemplate.queryForObject(sql, Integer.class, username, movieId, rank);

            }
            /*List<MovieDto> current*/

            if(favoritesId != 0) {
                MovieDto movieDto = movieService.getMovie(movieId);
//                MovieDto dto = getFavoriteMovie(movieId, username);
                movieDto.setWatched(false);
                movieDto.setFavorited(true);
                movieDto.setRank(rank);
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
                MovieDto movieDto = movieService.getMovie(rowSet.getInt("movie_id"));
                MovieDto movie = mapRowToMovieDto(rowSet);
                movieDto.setWatched(movie.isWatched());
                movieDto.setFavorited(movie.isFavorited());
                movieDto.setRank(movie.getRank());
                return movieDto;
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
            MovieDto movie = getFavoriteMovie(movieId, username);
            int rowsAffected = jdbcTemplate.update(sql, movieId, username);
            if(rowsAffected != 0) {
                MovieDto movieDto = movieService.getMovie(movieId);
                movieDto.setWatched(false);
                movieDto.setFavorited(false);
                if(movie.getRank() != 0) {
                    fixRanks(username, movie.getRank());
                }
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

    @Override
    public List<MovieDto> getFavorites(String username) {
        String sql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "WHERE users.username = ?";
        List<MovieDto> favoriteMovies = new ArrayList<>();

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            while (rowSet.next()) {
                MovieDto movieDto = movieService.getMovie(rowSet.getInt("movie_id"));
                MovieDto movie = mapRowToMovieDto(rowSet);
                movieDto.setWatched(movie.isWatched());
                movieDto.setFavorited(movie.isFavorited());
                movieDto.setRank(movie.getRank());
                favoriteMovies.add(movieDto);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

        return favoriteMovies;
    }

    @Override
    public List<MovieDto> getWatchList(String username) {
        String sql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "WHERE users.username = ? AND watched = false";
        List<MovieDto> watchedList = new ArrayList<>();

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            while (rowSet.next()) {
                MovieDto movieDto = movieService.getMovie(rowSet.getInt("movie_id"));
                MovieDto movie = mapRowToMovieDto(rowSet);
                movieDto.setWatched(movie.isWatched());
                movieDto.setFavorited(movie.isFavorited());
                movieDto.setRank(movie.getRank());
                watchedList.add(movieDto);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

        return watchedList;
    }

    @Override
    public MovieDto updateWatched(int movieId, String username){

        String sql = "UPDATE favorites SET watched = ? " +
                "FROM users " +
                "WHERE users.user_id = favorites.user_id AND users.username = ? AND favorites.movie_id = ?;";

        try {
            MovieDto movieDto = getFavoriteMovie(movieId, username);
            if(movieDto != null) {
                jdbcTemplate.update(sql, !movieDto.isWatched(), username, movieId);
            }
            else {
                movieDto = favoriteMovie(movieId, username);
                jdbcTemplate.update(sql, !movieDto.isWatched(), username, movieId);
            }
            movieDto.setWatched(!movieDto.isWatched());
            return movieDto;
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    @Override
    public List<MovieDto> getSeenList(String username) {
        String sql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "WHERE users.username = ? AND watched = true";
        List<MovieDto> watchedList = new ArrayList<>();

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            while (rowSet.next()) {
                MovieDto movieDto = movieService.getMovie(rowSet.getInt("movie_id"));
                MovieDto movie = mapRowToMovieDto(rowSet);
                movieDto.setWatched(movie.isWatched());
                movieDto.setFavorited(movie.isFavorited());
                movieDto.setRank(movie.getRank());
                watchedList.add(movieDto);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

        return watchedList;
    }

    @Override
    public List<MovieDto> updateRank(int movieId, int rank, String username){
        List<MovieDto> updatedRanks = new ArrayList<>();
        String secondSql = "UPDATE favorites set rank = ? FROM users WHERE users.user_id = favorites.user_id AND users.username = ? AND favorites.rank = ?";
        String sql = "UPDATE favorites SET rank = ? FROM users WHERE users.user_id = favorites.user_id AND users.username = ? AND favorites.movie_id = ?";

        try {
            MovieDto movieDto = getFavoriteMovie(movieId, username);
            int firstUpdate = jdbcTemplate.update(secondSql, movieDto.getRank(), username, rank);
            int secondUpdate = jdbcTemplate.update(sql, rank, username, movieId);
            if(firstUpdate != 0 && secondUpdate != 0){
                return getFavorites(username);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    @Override
    public List<MovieDto> getRecommendations(String username) {
        String sql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "WHERE users.username = ?";
        List<MovieDto> favoriteMovies = new ArrayList<>();

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            List<Integer> idsList = new ArrayList<>();
            while (rowSet.next()) {
                idsList.add(rowSet.getInt("movie_id"));
            }
            return movieService.getRecommendations(idsList);
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), sql, e.getSQLException());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }


    private void fixRanks(String username, int index) {
        String unrankedSql = "SELECT * FROM favorites " +
                "JOIN users ON users.user_id = favorites.user_id " +
                "Where users.username = ? AND favorites.rank = 0;";
        String updateSql = "UPDATE favorites SET rank = ? " +
                "FROM users " +
                "WHERE users.user_id = favorites.user_id AND users.username = ? AND favorites.rank = ?";

        List<MovieDto> rankedFavorites = new ArrayList<>();

        try{
            while(index < 10) {
                jdbcTemplate.update(updateSql, index, username, index+1);
                index++;
            }

            SqlRowSet unrankedRowSet = jdbcTemplate.queryForRowSet(unrankedSql, username);

            if(unrankedRowSet.next()) {
                jdbcTemplate.update(updateSql + " AND favorites.movie_id = ?", 10, username, 0, unrankedRowSet.getInt("movie_id"));
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new BadSqlGrammarException(e.getMessage(), updateSql, e.getSQLException());
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
