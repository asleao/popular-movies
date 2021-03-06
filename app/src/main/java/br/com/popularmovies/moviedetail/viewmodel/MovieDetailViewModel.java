package br.com.popularmovies.moviedetail.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieDetailViewModel extends ViewModel {
    private final LiveData<Resource<Void>> favorites;
    private final LiveData<Resource<Movie>> mMovie;
    private final MutableLiveData<Boolean> movieStatus = new MutableLiveData<>();
    private final MutableLiveData<Integer> _movieId = new MutableLiveData<>();
    private Movie movie;

    public MovieDetailViewModel(final MovieRepository mMovieRepository, final int movieId) {
        mMovie = Transformations.switchMap(_movieId, new Function<Integer, LiveData<Resource<Movie>>>() {
            @Override
            public LiveData<Resource<Movie>> apply(Integer input) {
                return mMovieRepository.getMovie(movieId);
            }
        });
        _movieId.setValue(movieId);
        favorites = Transformations.switchMap(movieStatus, new Function<Boolean, LiveData<Resource<Void>>>() {
            @Override
            public LiveData<Resource<Void>> apply(Boolean isFavorite) {
                if (isFavorite != null) {
                    movie.setFavorite(isFavorite);
                    if (isFavorite) {
                        return mMovieRepository.saveMovie(movie);
                    } else {
                        return mMovieRepository.removeMovie(movie);
                    }
                }
                return null;
            }
        });
    }


    public LiveData<Resource<Void>> getFavorites() {
        return favorites;
    }

    public LiveData<Resource<Movie>> getmMovie() {
        return mMovie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void saveFavorites(boolean status) {
        this.movieStatus.setValue(status);
    }

    public void tryAgain() {
        _movieId.setValue(_movieId.getValue());
    }
}
