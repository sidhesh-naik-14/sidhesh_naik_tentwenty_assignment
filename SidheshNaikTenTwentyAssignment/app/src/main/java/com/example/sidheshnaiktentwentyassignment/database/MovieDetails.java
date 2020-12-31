package com.example.sidheshnaiktentwentyassignment.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_details_table")
public class MovieDetails {

    @PrimaryKey
    @NonNull
    private Integer id;
    private String title;
    private String poster_path;
    private String release_date;
    private boolean adult;
    private String genres;
    private String videoId;
    private String overView;
    private boolean movieDetailsUpdated;

    public MovieDetails(@NonNull Integer id, String title, String poster_path, String release_date, boolean adult, String genres, String videoId, String overView, boolean movieDetailsUpdated) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.adult = adult;
        this.genres = genres;
        this.videoId = videoId;
        this.overView = overView;
        this.movieDetailsUpdated = movieDetailsUpdated;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public boolean isMovieDetailsUpdated() {
        return movieDetailsUpdated;
    }

    public void setMovieDetailsUpdated(boolean movieDetailsUpdated) {
        this.movieDetailsUpdated = movieDetailsUpdated;
    }
}