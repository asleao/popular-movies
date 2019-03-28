package br.com.popularmovies.moviedetail.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.popularmovies.R;
import br.com.popularmovies.movies.ui.MovieFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MovieDetailFragment movieDetailFragment =
                (MovieDetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fg_movie);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fg_moviedetail, movieDetailFragment);
            transaction.commit();
        }
    }
}
