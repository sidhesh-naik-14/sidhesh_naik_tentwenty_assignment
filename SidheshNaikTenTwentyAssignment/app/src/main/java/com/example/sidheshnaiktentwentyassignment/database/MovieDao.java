package com.example.sidheshnaiktentwentyassignment.database;

import com.example.sidheshnaiktentwentyassignment.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {
    @Insert
    void insert(MovieDetails movieDetails);

    @Query("DELETE From movie_details_table WHERE id = :movieId")
    void delete(int movieId);

    @Query("SELECT * FROM movie_details_table")
    LiveData<List<Movie>> getMovieList();

    @Query("SELECT * FROM movie_details_table WHERE id = :movieId ")
    MovieDetails getMovie(int movieId);
}
