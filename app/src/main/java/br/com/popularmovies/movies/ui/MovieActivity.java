package br.com.popularmovies.movies.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.popularmovies.R;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        MovieFragment movieFragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fg_movie);
        if (movieFragment == null) {
            movieFragment = MovieFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fg_movie, movieFragment);
            transaction.commit();
        }
    }
}
