package br.com.popularmovies.moviedetail.trailers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.utils.validateResponse
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

    private val _trailers = MutableLiveData<MovieTrailers>()
    val trailers: LiveData<MovieTrailers> = _trailers

    init {
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch {
            showLoading(true)
            val resource = mMovieRepository.getMovieTrailers(movieId)
            resource.validateResponse(_trailers, _error)
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getTrailers()
    }
}