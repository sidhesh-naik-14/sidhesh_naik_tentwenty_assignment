package com.example.sidheshnaiktentwentyassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sidheshnaiktentwentyassignment.R;
import com.example.sidheshnaiktentwentyassignment.database.MovieDetails;
import com.example.sidheshnaiktentwentyassignment.databinding.MovieRowItemBinding;
import com.example.sidheshnaiktentwentyassignment.model.Movie;
import com.example.sidheshnaiktentwentyassignment.utils.Common;
import com.example.sidheshnaiktentwentyassignment.utils.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> {
    private ArrayList<MovieDetails> moviesList;
    private Context mContext;
    private MovieListRowClickListener movieListRowClickListener;

    public MoviesListAdapter(Context mContext, ArrayList<MovieDetails> moviesList, MovieListRowClickListener movieListRowClickListener) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.movieListRowClickListener = movieListRowClickListener;
    }

    @NonNull
    @Override
    public MoviesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        com.example.sidheshnaiktentwentyassignment.databinding.MovieRowItemBinding binding = MovieRowItemBinding.inflate(inflater, parent, false);
        return new MoviesListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListViewHolder holder, int position) {
        holder.binding.movieTitleTextView.setText(moviesList.get(position).getTitle());
        if (moviesList.get(position).isAdult()) {
            holder.binding.movieGradeTextView.setText(mContext.getString(R.string.adult_text));
        } else {
            holder.binding.movieGradeTextView.setText(mContext.getString(R.string.non_adult_text));
        }
        holder.binding.movieReleaseDateTextView.setText(Common.getExpectedDateFormat("YYYY-MM-DD", "dd MMM yyyy", moviesList.get(position).getRelease_date()));
        Glide.with(mContext).load(Constants.IMAGE_BASE_URL + moviesList.get(position).getPoster_path())
                .into(holder.binding.posterImageView);
        holder.itemView.setOnClickListener(view -> {
            movieListRowClickListener.didClickMovieListRow(moviesList.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    static class MoviesListViewHolder extends RecyclerView.ViewHolder {

        private MovieRowItemBinding binding;

        MoviesListViewHolder(MovieRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setList(ArrayList<MovieDetails> list) {
        moviesList = list;
        notifyDataSetChanged();
    }

    public interface MovieListRowClickListener {
        void didClickMovieListRow(int movieId);
    }
}
