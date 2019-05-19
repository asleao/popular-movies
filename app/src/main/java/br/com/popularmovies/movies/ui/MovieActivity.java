package br.com.popularmovies.movies.ui;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MovieRepository.destroyInstance();
        MovieLocalDataSource.destroyInstance();
        MovieRemoteDataSource.destroyInstance();
    }
}
