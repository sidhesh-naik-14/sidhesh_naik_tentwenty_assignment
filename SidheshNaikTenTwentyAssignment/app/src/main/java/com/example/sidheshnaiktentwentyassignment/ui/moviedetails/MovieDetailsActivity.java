package com.example.sidheshnaiktentwentyassignment.ui.moviedetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
            binding.youtubePlayer.exitFullScreen();
            binding.youtubePlayerView.setVisibility(View.GONE);
        });
    }

    private void getIntentData() {
       movieId = getIntent().getIntExtra(Constants.MOVIE_ID_KEY,0);
    }

    private void getMovieDetails() {
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getMovieDetails(movieId,queryMap);
    }

    private void observeData() {

        viewModel.getMovie().observe(this, movie -> {
            binding.progressBar.setVisibility(View.GONE);
            Glide.with(this).load(Constants.IMAGE_BASE_URL + movie.getPoster_path())
                    .centerCrop()
                    .into(binding.posterImageView);

            binding.movieTitleTextView.setText(movie.getTitle());


            StringBuilder genresText = new StringBuilder();
            for (int i = 0; i < movie.getGenres().size(); i++){
                if(i ==  movie.getGenres().size() -1)
                    genresText.append(movie.getGenres().get(i).getName());
                else
                    genresText.append(movie.getGenres().get(i).getName()).append(", ");
            }

            binding.genresTextView.setText(genresText.toString());
            binding.overviewTextView.setText(movie.getOverview());
            binding.movieReleaseDateTextView.setText(Common.getExpectedDateFormat("YYYY-MM-DD", "dd MMM yyyy", movie.getRelease_date()));
            JsonArray array = movie.getVideos().getAsJsonArray("results");
            videoId = array.get(0).getAsJsonObject().get("key").getAsString();
            viewModel.updateMovieDetails(movieId, genresText.toString(),videoId,movie.getOverview());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
