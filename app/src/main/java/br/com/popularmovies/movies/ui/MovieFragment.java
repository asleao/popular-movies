package br.com.popularmovies.movies.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import br.com.popularmovies.R;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.moviedetail.ui.MovieDetailActivity;
import br.com.popularmovies.movies.adapters.MovieAdapter;
import br.com.popularmovies.movies.data.response.Movie;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.viewmodel.MovieViewModel;

import static br.com.popularmovies.movies.Constants.FILTER_HIGHEST_RATED;
import static br.com.popularmovies.movies.Constants.FILTER_MOST_POPULAR;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;
import static br.com.popularmovies.movies.Constants.IMAGE_URL;
import static br.com.popularmovies.movies.Constants.INDEX_FILTER_HIGHEST_RATED;
import static br.com.popularmovies.movies.Constants.INDEX_FILTER_MOST_POPULAR;
import static br.com.popularmovies.movies.Constants.MOVIE_DATE_PATTERN;
import static br.com.popularmovies.movies.Constants.MOVIE_OVERVIEW;
import static br.com.popularmovies.movies.Constants.MOVIE_POSTER;
import static br.com.popularmovies.movies.Constants.MOVIE_RATING;
import static br.com.popularmovies.movies.Constants.MOVIE_RELEASE_DATE;
import static br.com.popularmovies.movies.Constants.MOVIE_TITLE;
import static br.com.popularmovies.movies.Constants.TITLE_DIALOG_FILTER;

public class MovieFragment extends Fragment implements MovieAdapter.MovieClickListener {

    private MovieViewModel mViewModel;
    private RecyclerView mMoviesRecyclerView;
    private Observer<Resource<Movies>> moviesObserver;
    private Group mNoConnectionGroup;
    private Button mTryAgainButton;
    private TextView mNoConnectionText;
    private ProgressBar mProgressBar;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        setupObservers();
    }

    private void setupObservers() {
        moviesObserver = new Observer<Resource<Movies>>() {
            @Override
            public void onChanged(@Nullable Resource<Movies> moviesResource) {
                if (moviesResource != null)
                    switch (moviesResource.status) {
                        case LOADING:
                            showLoading();
                            break;
                        case SUCCESS:
                            hideLoading();
                            if (moviesResource.data != null) {
                                MovieAdapter mMovieAdapter = new MovieAdapter(moviesResource.data.getMovies(), MovieFragment.this);
                                mMoviesRecyclerView.setAdapter(mMovieAdapter);
                                showResult();
                            }
                            break;
                        case ERROR:
                            hideLoading();
                            ErrorResponse error = moviesResource.error;
                            if (error != null) {
                                if (error.getStatusCode() == 503) {
                                    showNoConnection(error.getStatusMessage());
                                    tryAgain();
                                } else {
                                    showGenericError(error.getStatusMessage());
                                }
                            }
                            break;
                    }
            }
        };
    }


    private void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.GONE);
        mNoConnectionGroup.setVisibility(View.GONE);
    }

    private void showResult() {
        changeComponentVisibility(View.GONE, View.VISIBLE);
    }

    private void showNoConnection(String message) {
        mNoConnectionText.setText(message);
        changeComponentVisibility(View.VISIBLE, View.GONE);
    }

    private void showGenericError(String message) {
        final AlertDialog sortDialog = new AlertDialog.Builder(getContext())
                .setTitle(GENERIC_MSG_ERROR_TITLE)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create();

        sortDialog.show();
    }

    private void changeComponentVisibility(int gone, int visible) {
        mNoConnectionGroup.setVisibility(gone);
        mMoviesRecyclerView.setVisibility(visible);
    }


    private void tryAgain() {
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.tryAgain();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setupFields(view);
        setupMoviesList(view);
        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mViewModel.getMovies().observe(getViewLifecycleOwner(), moviesObserver);
        return view;
    }

    private void setupMoviesList(View view) {
        mMoviesRecyclerView = view.findViewById(R.id.rv_movies);
        mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
    }

    private void setupFields(View view) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection);
        mNoConnectionText = view.findViewById(R.id.tv_no_conection);
        mTryAgainButton = view.findViewById(R.id.bt_try_again);
        mProgressBar = view.findViewById(R.id.pb_movies);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.m_sort) {
            CharSequence[] values = {"Most Popular", "Highest Rated"};
            final AlertDialog sortDialog = new AlertDialog.Builder(getContext())
                    .setTitle(TITLE_DIALOG_FILTER)
                    .setSingleChoiceItems(values, mViewModel.getSelectedFilterIndex(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeSortOrder(which);
                            dialog.dismiss();
                        }
                    })
                    .create();

            sortDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeSortOrder(int item) {
        switch (item) {
            case INDEX_FILTER_MOST_POPULAR:
                mViewModel.setMovieSortBy(FILTER_MOST_POPULAR);
                mViewModel.setSelectedFilterIndex(0);
                break;
            case INDEX_FILTER_HIGHEST_RATED:
                mViewModel.setMovieSortBy(FILTER_HIGHEST_RATED);
                mViewModel.setSelectedFilterIndex(1);
                break;
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MOVIE_TITLE, movie.getOriginalTitle());
        intent.putExtra(MOVIE_POSTER, IMAGE_URL + movie.getPoster());
        if (movie.getReleaseDate() != null) {
            intent.putExtra(MOVIE_RELEASE_DATE, movie.getReleaseDate()
                    .toString(MOVIE_DATE_PATTERN, Locale.getDefault()));
        }
        intent.putExtra(MOVIE_RATING, movie.getVoteAverage().toString());
        intent.putExtra(MOVIE_OVERVIEW, movie.getOverview());
        startActivity(intent);
    }
}
