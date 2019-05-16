package br.com.popularmovies.moviedetail.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieDetailViewModel extends ViewModel {
    private LiveData<Resource<Boolean>> isFavorite;
    private MutableLiveData<Boolean> movieStatus = new MutableLiveData<>();

    public MovieDetailViewModel(final MovieRepository mMovieRepository, final int movieId) {
        isFavorite = Transformations.switchMap(movieStatus, new Function<Boolean, LiveData<Resource<Boolean>>>() {
                    @Override
                    public LiveData<Resource<Boolean>> apply(Boolean status) {
                        return status == null ? null : mMovieRepository.saveToFavorites(movieId, status);
                    }
                }
        );
    }

    public LiveData<Resource<Boolean>> getIsFavorite() {
        return isFavorite;
    }

    public void saveFavorites(boolean status) {
        this.movieStatus.setValue(status);
    }
}
