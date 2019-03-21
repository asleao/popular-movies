package br.com.popularmovies.movies.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.data.source.MovieRepository;
import br.com.popularmovies.movies.data.source.remote.MovieRemoteDataSource;

public class MovieViewModel extends ViewModel {
    private LiveData<Resource<Movies>> mMovies;
    private MutableLiveData<String> mSortBy;
    private MovieRepository mMovieRepository;
    private int selectedFilterIndex = 0;


    public MovieViewModel() {
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mSortBy = new MutableLiveData<>();
        mMovies = Transformations.switchMap(mSortBy, new Function<String, LiveData<Resource<Movies>>>() {
            @Override
            public LiveData<Resource<Movies>> apply(String input) {
                return getMoviesSortedBy(input);
            }
        });
        setMovieSortBy("popularity.desc");
    }

    public LiveData<Resource<Movies>> getMovies() {
        return mMovies;
    }

    public void setMovies(LiveData<Resource<Movies>> movies) {
        mMovies = movies;
    }

    public void setMovieSortBy(String sortBy) {
        mSortBy.setValue(sortBy);
    }


    public int getSelectedFilterIndex() {
        return selectedFilterIndex;
    }

    public void setSelectedFilterIndex(int selectedFilterIndex) {
        this.selectedFilterIndex = selectedFilterIndex;
    }

    public LiveData<Resource<Movies>> getMoviesSortedBy(String field) {
        return mMovieRepository.getMovies(field);
    }
}
