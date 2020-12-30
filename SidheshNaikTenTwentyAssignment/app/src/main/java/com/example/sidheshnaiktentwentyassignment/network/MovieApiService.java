package com.example.sidheshnaiktentwentyassignment.network;

import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.model.MovieResponse;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MovieApiService {

    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcomingMovieList(@QueryMap HashMap<String,String> queries);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") int id, @QueryMap HashMap<String,String> queries);
}
