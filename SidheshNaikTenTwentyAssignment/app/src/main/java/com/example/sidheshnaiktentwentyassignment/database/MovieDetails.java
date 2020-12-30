package com.example.sidheshnaiktentwentyassignment.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_details_table")
public class MovieDetails {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer rowId;
    private Integer movieId;
    private String title;
    private String poster_path;
    private String release_date;
    private boolean adult;

    public MovieDetails(Integer movieId, String title, String poster_path, String release_date, boolean adult) {
        this.movieId = movieId;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.adult = adult;
    }

    @NonNull
    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(@NonNull Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
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
}