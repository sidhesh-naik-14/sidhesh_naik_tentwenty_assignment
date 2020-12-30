package com.example.sidheshnaiktentwentyassignment.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

public interface MovieDao {
    @Insert
    void insert(MovieList movieList);

    @Query("DELETE From movie_list_table WHERE id = :movieId")
    void delete(int movieId);

    @Query("SELECT * FROM movie_list_table")
    LiveData<List<MovieList>> getMovieList();

    @Query("SELECT * FROM movie_list_table WHERE id = :movieId ")
    MovieList getMovie(int movieId);
}
