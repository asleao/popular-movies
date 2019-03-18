package br.com.popularmovies.movies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        if (container != null) {
            mMoviesRecyclerView = view.findViewById(R.id.rv_movies);
            mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mViewModel.getMovies().observe(this, new Observer<Resource<Movies>>() {
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
        });

    }

}
