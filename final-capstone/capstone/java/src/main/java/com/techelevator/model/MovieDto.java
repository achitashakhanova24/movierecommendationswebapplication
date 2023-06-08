package com.techelevator.model;

public class MovieDto {
    private String title;
    private String genres;
    private String releaseDate;
    private String description;
    private String language;
    private int movieId;
    private boolean favorited;
    private boolean watched;
    private int rank;
    private String posterPath;
    private String backdropPath;
    public double rating;
    

    public MovieDto(String title, String genres, String releaseDate, String description, String language, int movieId, boolean favorited, boolean watched, int rank,String posterPath, String backdropPath, double rating){
        this.title = title;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.description = description;
        this.language = language;
        this.movieId = movieId;
        this.favorited = favorited;
        this.watched = watched;
        this.rank = rank;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.rating = rating;
    }

    public MovieDto(){}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
