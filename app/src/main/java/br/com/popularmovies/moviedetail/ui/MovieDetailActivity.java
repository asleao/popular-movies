package br.com.popularmovies.moviedetail.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import br.com.popularmovies.R;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;
import br.com.popularmovies.utils.FragmentUtils;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        FragmentUtils.addFragmentInActivity(getSupportFragmentManager(),
                MovieDetailFragment.Companion.newInstance(),
                R.id.fg_moviedetail,
                getResources().getString(R.string.fg_movie_detail_tag),
                false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MovieRepository.destroyInstance();
        MovieLocalDataSource.destroyInstance();
        MovieRemoteDataSource.destroyInstance();
    }
}
