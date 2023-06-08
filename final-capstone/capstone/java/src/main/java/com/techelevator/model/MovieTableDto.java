package com.techelevator.model;

public class MovieTableDto {
    private String title;
    public double rating;
    private String genres;
    private String releaseDate;
    private String description;
    private String language;
    private int movieId;

    public MovieTableDto(){}

    public MovieTableDto(String title, String genres, String releaseDate, String description, String language, double rating, int movieId){
        this.title = title;
        this.rating = rating;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.description = description;
        this.language = language;
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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