package com.example.sidheshnaiktentwentyassignment.ui.moviedetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sidheshnaiktentwentyassignment.database.MovieDetails;
import com.example.sidheshnaiktentwentyassignment.databinding.ActivityMovieDetailsBinding;
import com.example.sidheshnaiktentwentyassignment.ui.base.BaseActivity;
import com.example.sidheshnaiktentwentyassignment.utils.Common;
import com.example.sidheshnaiktentwentyassignment.utils.Constants;
import com.example.sidheshnaiktentwentyassignment.viewmodel.MovieViewModel;
import com.google.gson.JsonArray;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.HashMap;

import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieDetailsActivity extends BaseActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieViewModel viewModel;
    String videoId;
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
        setButtonClickListener();
        getIntentData();
        observeData();
        getMovieDetails();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void setButtonClickListener() {
        binding.watchTrailerButton.setOnClickListener(view -> {
            if(videoId != null){
                binding.youtubePlayerView.setVisibility(View.VISIBLE);
                getLifecycle().addObserver(binding.youtubePlayer);
                binding.youtubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                        super.onError(youTubePlayer, error);
                        Toast.makeText(MovieDetailsActivity.this,"Some error occurred!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        youTubePlayer.loadVideo(videoId, 0);

                    }
                });
                binding.youtubePlayer.enterFullScreen();
            }
            else
                Toast.makeText(this,"Sorry trailer not found!",Toast.LENGTH_SHORT).show();
        });

        binding.doneButton.setOnClickListener(view -> {
            stopPlayer();
        });
    }

    private void getIntentData() {
       movieId = getIntent().getIntExtra(Constants.MOVIE_ID_KEY,0);
    }

    private void stopPlayer(){
        binding.youtubePlayer.release();
        binding.youtubePlayerView.setVisibility(View.GONE);
    }

    private void getMovieDetails() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (isMovieDetailsStoredInDatabase()) {
            populateMovieDetails(viewModel.getSavedMovieDetails(movieId));
        } else {
            viewModel.getMovieDetails(movieId,queryMap);
        }
    }

    private void populateMovieDetails(MovieDetails movieDetails){
        Glide.with(this).load(Constants.IMAGE_BASE_URL + movieDetails.getPoster_path())
                .centerCrop()
                .into(binding.posterImageView);
        binding.movieTitleTextView.setText(movieDetails.getTitle());
        binding.genresTextView.setText(movieDetails.getGenres());
        binding.overviewTextView.setText(movieDetails.getOverView());
        binding.movieReleaseDateTextView.setText(Common.getExpectedDateFormat("YYYY-MM-DD", "dd MMM yyyy", movieDetails.getRelease_date()));
        videoId = movieDetails.getVideoId();
        binding.progressBar.setVisibility(View.GONE);
    }

    private void observeData() {

        viewModel.getMovie().observe(this, movie -> {
            StringBuilder genresText = new StringBuilder();
            for (int i = 0; i < movie.getGenres().size(); i++){
                if(i ==  movie.getGenres().size() -1)
                    genresText.append(movie.getGenres().get(i).getName());
                else
                    genresText.append(movie.getGenres().get(i).getName()).append(", ");
            }
            JsonArray array = movie.getVideos().getAsJsonArray("results");
            videoId = array.get(0).getAsJsonObject().get("key").getAsString();
            viewModel.updateMovieDetails(movieId, genresText.toString(),videoId,movie.getOverview());
            setFlagMovieDetailsStoredInDatabase();
            populateMovieDetails(new MovieDetails(movie.getId(),movie.getTitle(),movie.getPoster_path(),movie.getRelease_date(),movie.isAdult(),genresText.toString(),videoId,movie.getOverview()));
        });
    }

    private void setFlagMovieDetailsStoredInDatabase() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.MOVIE_DETAILS_STORED_IN_DB_KEY, true);
        editor.apply();
    }

    private boolean isMovieDetailsStoredInDatabase() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Constants.MOVIE_DETAILS_STORED_IN_DB_KEY, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBackPressed() {
        if (binding.youtubePlayerView.getVisibility() == View.VISIBLE) {
            stopPlayer();
        } else {
            super.onBackPressed();
        }
    }
}

