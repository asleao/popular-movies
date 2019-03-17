package br.com.popularmovies.movies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.popularmovies.R;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.viewmodel.MovieViewModel;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getMovies().observe(this, new Observer<Resource<Movies>>() {
            @Override
            public void onChanged(@Nullable Resource<Movies> moviesResource) {
                if (moviesResource != null)
                    switch (moviesResource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            break;
                        case ERROR:
                            break;
                    }
            }
        });
    }

}
