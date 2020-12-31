package com.example.sidheshnaiktentwentyassignment.database;

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
    LiveData<List<MovieDetails>> getMovieList();

    @Query("SELECT * FROM movie_details_table WHERE id = :movieId ")
    MovieDetails getMovie(int movieId);

    @Query("UPDATE movie_details_table SET genres = :genres ,videoId= :videoId,overView= :overView WHERE id LIKE :movieId ")
    void updateMovieDetails(int movieId,String genres, String videoId, String overView);
}
