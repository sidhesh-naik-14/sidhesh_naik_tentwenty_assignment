package com.example.sidheshnaiktentwentyassignment.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Movie {
    private Integer id;
    private String title;
    private String poster_path;
    private String release_date;
    private boolean adult;
    private ArrayList<Integer> genre_ids;
    private ArrayList<String> genre_names;
    private ArrayList<Genre> genres;
    private JsonObject videos ;
    private String overview ;

    public Movie(Integer id, String title, String poster_path, String release_date, boolean adult, ArrayList<Integer> genre_ids, ArrayList<String> genre_names, ArrayList<Genre> genres, JsonObject videos, String overview) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.adult = adult;
        this.genre_ids = genre_ids;
        this.genre_names = genre_names;
        this.genres = genres;
        this.videos = videos;
        this.overview = overview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public ArrayList<String> getGenre_names() {
        return genre_names;
    }

    public void setGenre_names(ArrayList<String> genre_names) {
        this.genre_names = genre_names;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public JsonObject getVideos() {
        return videos;
    }

    public void setVideos(JsonObject videos) {
        this.videos = videos;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
