package com.example.sidheshnaiktentwentyassignment.repository;

import com.example.sidheshnaiktentwentyassignment.database.MovieDao;
import com.example.sidheshnaiktentwentyassignment.database.MovieDetails;
import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.model.MovieResponse;
import com.example.sidheshnaiktentwentyassignment.network.MovieApiService;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private static final String TAG = "Repository";

    private MovieApiService apiService;
    private MovieDao movieDao;

    @Inject
    public Repository(MovieApiService apiService, MovieDao movieDao) {
        this.apiService = apiService;
        this.movieDao= movieDao;
    }

    public Observable<MovieResponse> getUpcoming(HashMap<String, String> map){
        return apiService.getUpcomingMovieList(map);
    }

    public Observable<Movie>  getMovieDetails(int movieId, HashMap<String, String> map){
        return apiService.getMovieDetails(movieId, map);
    }

    public void insertMovie(MovieDetails movieDetails){
        movieDao.insert(movieDetails);
    }

    public MovieDetails getMovieDetails(int movieId){
        return movieDao.getMovie(movieId);
    }

    public boolean isMovieDetailsUpdated(int movieId){
        return movieDao.isMovieDetailsUpdated(movieId);
    }

    public void updateMovieDetails(int movieId, String genres, String videoId, String overView,boolean movieDetailsUpdated){
        movieDao.updateMovieDetails(movieId, genres, videoId,overView,movieDetailsUpdated);
    }

    public LiveData<List<MovieDetails>> getSavedMovieList(){
        return  movieDao.getMovieList();
    }


}
