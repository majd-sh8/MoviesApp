package application.example.com.moviesapp.adapters;


import android.widget.ImageView;

import application.example.com.moviesapp.models.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView);

}

