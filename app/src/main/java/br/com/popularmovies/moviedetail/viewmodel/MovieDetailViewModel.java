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
    private LiveData<Resource<Void>> result;
    private MutableLiveData<Boolean> movieStatus = new MutableLiveData<>();

    public MovieDetailViewModel(final MovieRepository mMovieRepository, final Movie movie) {
        result = Transformations.switchMap(movieStatus, new Function<Boolean, LiveData<Resource<Void>>>() {
            @Override
            public LiveData<Resource<Void>> apply(Boolean isFavorite) {
                if (isFavorite != null) {
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


    public LiveData<Resource<Void>> getResult() {
        return result;
    }

    public MutableLiveData<Boolean> getMovieStatus() {
        return movieStatus;
    }

    public void saveFavorites(boolean status) {
        this.movieStatus.setValue(status);
    }
}
