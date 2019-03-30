package br.com.popularmovies.movies.data.source;

import android.arch.lifecycle.LiveData;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;

public interface MovieDataSource {

    LiveData<Resource<Movies>> getMovies(String orderBy);
}
