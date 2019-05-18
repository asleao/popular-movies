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
    private LiveData<Resource<Void>> favorites;
    private LiveData<Resource<Movie>> mMovie;
    private MutableLiveData<Boolean> movieStatus = new MutableLiveData<>();
    private Movie movie;

    public MovieDetailViewModel(final MovieRepository mMovieRepository, final int movieId) {
        mMovie = mMovieRepository.getMovie(movieId);
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
}
