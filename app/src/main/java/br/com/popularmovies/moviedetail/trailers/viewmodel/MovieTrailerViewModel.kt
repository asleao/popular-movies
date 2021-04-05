package br.com.popularmovies.moviedetail.trailers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.Error
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.repositories.movie.MovieRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieTrailerViewModel @AssistedInject constructor(
        private val mMovieRepository: MovieRepository,
        @Assisted val movieId: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieTrailerViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _trailers = MutableLiveData<List<MovieTrailer>>()
    val trailers: LiveData<List<MovieTrailer>> = _trailers

    init {
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch {
            showLoading(true)
            when (val result = mMovieRepository.getMovieTrailers(movieId)) {
                is Result.Success -> _trailers.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getTrailers()
    }
}