package com.techelevator.model;

public class MovieDto {
    private String title;
    private String genres;
    private String releaseDate;
    private String description;
    private String language;

    public MovieDto(){}

    public MovieDto(String title, String genres, String releaseDate, String description, String language){
        this.title = title;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.description = description;
        this.language = language;
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
