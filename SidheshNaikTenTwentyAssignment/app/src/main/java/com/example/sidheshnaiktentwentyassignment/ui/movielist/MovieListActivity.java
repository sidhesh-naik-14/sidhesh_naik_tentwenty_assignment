package com.example.sidheshnaiktentwentyassignment.ui.movielist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sidheshnaiktentwentyassignment.adapters.MoviesListAdapter;
import com.example.sidheshnaiktentwentyassignment.database.MovieDetails;
import com.example.sidheshnaiktentwentyassignment.databinding.ActivityMovieListBinding;
import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.ui.base.BaseActivity;
import com.example.sidheshnaiktentwentyassignment.ui.moviedetails.MovieDetailsActivity;
import com.example.sidheshnaiktentwentyassignment.ui.ticketbookscreen.TicketBookingActivity;
import com.example.sidheshnaiktentwentyassignment.utils.Constants;
import com.example.sidheshnaiktentwentyassignment.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieListActivity extends BaseActivity implements MoviesListAdapter.MovieListRowClickListener {
    private ActivityMovieListBinding binding;
    private MovieViewModel viewModel;
    private MoviesListAdapter adapter;
    private HashMap<String, String> queryMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        queryMap.put("api_key", Constants.API_KEY);
        queryMap.put("page", "1");
        setUpRecyclerView();
        checkForRunTimePermissions();
        observeData();
    }


    private void checkForRunTimePermissions() {
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission, 1);
            } else {
                getMoviesList();
            }

        } else {
            getMoviesList();
        }
    }

    private void setUpRecyclerView() {
        binding.movieListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesListAdapter(this, new ArrayList<>(),  this);
        binding.movieListRecyclerView.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getUpcomingMoviesList().observe(this, movies -> {
            insertMovieDataIntoDatabase(movies);
            adapter.setList(convertToMovieDetailsArrayList(movies));
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private ArrayList<MovieDetails> convertToMovieDetailsArrayList(ArrayList<Movie> movies) {
        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
        for (int i=0;i < movies.size(); i++) {
            movieDetailsArrayList.add(new MovieDetails(movies.get(i).getId(),movies.get(i).getTitle(),movies.get(i).getPoster_path(),movies.get(i).getRelease_date(),movies.get(i).isAdult(),null,null,null,false));
        }
        return movieDetailsArrayList;
    }

    private void insertMovieDataIntoDatabase(ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            MovieDetails movieDetails = new MovieDetails(movies.get(i).getId(),movies.get(i).getTitle(),movies.get(i).getPoster_path(),movies.get(i).getRelease_date(),movies.get(i).isAdult(),null,null,null,false);
            viewModel.insertMovie(movieDetails);
        }
    }

    private void getMoviesList() {
        viewModel.getSavedMoviesList().observe(this, movieDetails -> {
            if (movieDetails == null || movieDetails.size() == 0) {
                binding.progressBar.setVisibility(View.VISIBLE);
                viewModel.getUpcomingMovies(queryMap);
            } else {
                adapter.setList((ArrayList<MovieDetails>) movieDetails);
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "All permission required.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                getMoviesList();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void didClickMovieListRow(int movieId) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID_KEY,movieId);
        startActivity(intent);
    }

    @Override
    public void didClickBookButton(int movieId) {
        Intent intent = new Intent(MovieListActivity.this, TicketBookingActivity.class);
        intent.putExtra(Constants.MOVIE_ID_KEY,movieId);
        startActivity(intent);
    }
}
