package br.com.popularmovies.moviedetail.trailers.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieTrailerViewModel extends ViewModel {
    private LiveData<Resource<MovieTrailers>> trailers;
    private MutableLiveData<Integer> movieId = new MutableLiveData<>();

    public MovieTrailerViewModel(final MovieRepository mMovieRepository, int movieId) {
        trailers = Transformations.switchMap(this.movieId, new Function<Integer, LiveData<Resource<MovieTrailers>>>() {
            @Override
            public LiveData<Resource<MovieTrailers>> apply(Integer input) {
                return input != null ? mMovieRepository.getMovieTrailers(input) : null;
            }
        });
        this.movieId.setValue(movieId);
    }

    public LiveData<Resource<MovieTrailers>> getTrailers() {
        return trailers;
    }

    public void tryAgain() {
        movieId.setValue(movieId.getValue());
    }
}
