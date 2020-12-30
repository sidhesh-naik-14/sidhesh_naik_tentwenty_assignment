package com.example.sidheshnaiktentwentyassignment.viewmodel;

import android.util.Log;

import com.example.sidheshnaiktentwentyassignment.database.MovieDetails;
import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {
    private static final String TAG = "MovieViewModel";

    private Repository repository;
    private MutableLiveData<ArrayList<Movie>> upcomingMoviesList = new MutableLiveData<>();
    private MutableLiveData<Movie> movieDetails = new MutableLiveData<>();
    private final io.reactivex.rxjava3.disposables.CompositeDisposable disposables = new CompositeDisposable();

    @ViewModelInject
    public MovieViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Movie>> getUpcomingMoviesList() {
        return upcomingMoviesList;
    }

    public MutableLiveData<Movie> getMovie() {
        return movieDetails;
    }

    public void getUpcomingMovies(HashMap<String, String> map) {
        disposables.add(repository.getUpcoming(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> upcomingMoviesList.setValue(result.getResults()),
                        error -> Log.e(TAG, "getUpcoming: " + error.getMessage()))
        );
    }

    public void getMovieDetails(int movieId, HashMap<String, String> map) {
        disposables.add(repository.getMovieDetails(movieId, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> movieDetails.setValue(result),
                        error -> Log.e(TAG, "getMovieDetails: " + error.getMessage()))
        );
    }

    // region : Rooms Methods
    public void insertMovie(MovieDetails movieDetails){
        Log.e(TAG, "insertMovie: " );
        repository.insertMovie(movieDetails);
    }

    public MovieDetails getMovieDetails(int movieId){
        return  repository.getMovieDetails(movieId);
    }

    public LiveData<List<Movie>> getSavedMoviesList() {
        return repository.getSavedMovieList();
    }

    // endregion

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
