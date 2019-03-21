package br.com.popularmovies.movies.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.popularmovies.R;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.adapters.MovieAdapter;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.viewmodel.MovieViewModel;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private RecyclerView mMoviesRecyclerView;
    private Observer<Resource<Movies>> moviesObserver;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        moviesObserver = new Observer<Resource<Movies>>() {
            @Override
            public void onChanged(@Nullable Resource<Movies> moviesResource) {
                if (moviesResource != null)
                    switch (moviesResource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            if (moviesResource.data != null) {
                                MovieAdapter mMovieAdapter = new MovieAdapter(moviesResource.data.getMovies());
                                mMoviesRecyclerView.setAdapter(mMovieAdapter);
                            }
                            break;
                        case ERROR:
                            break;
                    }
            }
        };
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (container != null) {
            mMoviesRecyclerView = view.findViewById(R.id.rv_movies);
            mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        }
        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mViewModel.getMovies().observe(getViewLifecycleOwner(), moviesObserver);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.m_sort) {
            CharSequence[] values = {"Popularity", "Top Rated"};
            final AlertDialog sortDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Sort By:")
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
            case 0:
                mViewModel.setMovieSortBy("popularity.desc");
                mViewModel.setSelectedFilterIndex(0);
                break;
            case 1:
                mViewModel.setMovieSortBy("vote_average.desc");
                mViewModel.setSelectedFilterIndex(1);
                break;
        }
    }
}
