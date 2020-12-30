package com.example.sidheshnaiktentwentyassignment.ui.moviedetails;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.sidheshnaiktentwentyassignment.databinding.ActivityMovieDetailsBinding;
import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.ui.base.BaseActivity;
import com.example.sidheshnaiktentwentyassignment.utils.Constants;
import com.example.sidheshnaiktentwentyassignment.viewmodel.MovieViewModel;

import java.util.HashMap;

import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsActivity extends BaseActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieViewModel viewModel;
    private Movie mMovie;
    private int movieId;
    private HashMap<String, String> queryMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        queryMap.put("api_key", Constants.API_KEY);
        queryMap.put("page", "1");
        queryMap.put("append_to_response","videos");
        getIntentData();

        observeData();
        viewModel.getMovieDetails(movieId,queryMap);
    }

    private void getIntentData() {
       movieId = getIntent().getIntExtra(Constants.MOVIE_ID_KEY,0);
    }

    private void observeData() {

        viewModel.getMovie().observe(this, movie -> {
            mMovie = movie;
            Glide.with(this).load(Constants.IMAGE_BASE_URL + movie.getPoster_path())
                    .centerCrop()
                    .into(binding.posterImageView);

            binding.movieTitleTextView.setText(movie.getTitle());


//            String genresText = "";
//            for (int i = 0; i < movie.getGenres().size(); i++){
//                if(i ==  movie.getGenres().size() -1)
//                    genresText+= movie.getGenres().get(i).getName();
//                else
//                    genresText+= movie.getGenres().get(i).getName() + " | ";
//            }
//
//            binding.movieGenre.setText(genresText);
//            binding.playTrailer.setVisibility(View.VISIBLE);
//            binding.movieCastText.setVisibility(View.VISIBLE);
//            binding.moviePlotText.setVisibility(View.VISIBLE);
//            isMovieInWishList(movieId);
//
//            JsonArray array = movie.getVideos().getAsJsonArray("results");
//            videoId = array.get(0).getAsJsonObject().get("key").getAsString();

        });
    }

    private void getMoviesList(int movieId) {
//        movieDetails =  viewModel.getSavedMovieDetails(movieId);
    }

    private void populateMovieData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
