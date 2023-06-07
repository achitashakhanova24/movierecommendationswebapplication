package com.techelevator.model;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String language;
    private List<String> listOfGenres;
    private int runtime;
    private Date releaseDate;
    private String description;
    private String posterPath;


    public Movie(int movieId, String title, String language, List<String> listOfGenres, int runtime, Date releaseDate, String description, String posterPath) {
        this.title = title;
        this.language = language;
        this.listOfGenres = listOfGenres;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.description = description;
        this.movieId = movieId;
        this.posterPath = posterPath;
    }

    public Movie(){}

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getListOfGenres() {
        return listOfGenres;
    }

    public void setListOfGenres(List<String> listOfGenres) {
        this.listOfGenres = listOfGenres;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
