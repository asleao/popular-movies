package br.com.popularmovies.moviedetail.trailers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.source.MovieRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class MovieTrailerViewModel @AssistedInject constructor(private val mMovieRepository: MovieRepository, @Assisted val movieId: Int) : ViewModel() {
    val trailers: LiveData<OldResource<MovieTrailers>>
    private val _movieId = MutableLiveData<Int>()

    fun tryAgain() {
        _movieId.value = movieId
    }

    init {
        trailers = Transformations.switchMap(_movieId) { input -> if (input != null) mMovieRepository.getMovieTrailers(input) else null }
        this._movieId.value = movieId
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieTrailerViewModel
    }
}