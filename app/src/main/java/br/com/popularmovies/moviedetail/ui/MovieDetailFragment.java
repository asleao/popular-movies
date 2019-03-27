package br.com.popularmovies.moviedetail.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.popularmovies.R;
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel;

import static br.com.popularmovies.movies.Constants.MOVIE_DURATION;
import static br.com.popularmovies.movies.Constants.MOVIE_OVERVIEW;
import static br.com.popularmovies.movies.Constants.MOVIE_POSTER;
import static br.com.popularmovies.movies.Constants.MOVIE_RATING;
import static br.com.popularmovies.movies.Constants.MOVIE_TITLE;
import static br.com.popularmovies.movies.Constants.MOVIE_YEAR;

public class MovieDetailFragment extends Fragment {

    private MovieDetailViewModel mViewModel;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieYear;
    private TextView mMovieDuration;
    private TextView mMovieRating;
    private TextView mMovieOverview;

    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        setupFields(view);
        Intent intent = requireActivity().getIntent();
        setData(intent);
        return view;
    }

    private void setupFields(View view) {
        mMovieTitle = view.findViewById(R.id.tv_movie_title);
        mMoviePoster = view.findViewById(R.id.iv_movie_poster);
        mMovieYear = view.findViewById(R.id.tv_movie_year);
        mMovieDuration = view.findViewById(R.id.tv_movie_duration);
        mMovieRating = view.findViewById(R.id.tv_movie_rating);
        mMovieOverview = view.findViewById(R.id.tv_movie_overview);
    }

    private void setData(Intent intent) {
        mMovieTitle.setText(intent.hasExtra(MOVIE_TITLE) ?
                intent.getStringExtra(MOVIE_TITLE) : "");
        String imageUrl = intent.hasExtra(MOVIE_POSTER) ?
                intent.getStringExtra(MOVIE_POSTER) : "";
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(mMoviePoster);
        mMovieYear.setText(intent.hasExtra(MOVIE_YEAR) ?
                intent.getStringExtra(MOVIE_YEAR) : "");
        mMovieDuration.setText(intent.hasExtra(MOVIE_DURATION) ?
                intent.getStringExtra(MOVIE_DURATION) : "");
        mMovieRating.setText(intent.hasExtra(MOVIE_RATING) ?
                intent.getStringExtra(MOVIE_RATING) : "");
        mMovieOverview.setText(intent.hasExtra(MOVIE_OVERVIEW) ?
                intent.getStringExtra(MOVIE_OVERVIEW) : "");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    }

}
