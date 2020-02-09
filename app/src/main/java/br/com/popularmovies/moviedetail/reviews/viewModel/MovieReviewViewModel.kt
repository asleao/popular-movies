package br.com.popularmovies.moviedetail.reviews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.utils.validateResponse
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieReviewViewModel @AssistedInject constructor(
        val mMovieRepository: MovieRepository,
        @Assisted val movieId: Int
) : ViewModel() {
    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error
    private val _reviews = MutableLiveData<MovieReviews>()
    val reviews: LiveData<MovieReviews>
        get() = _reviews

    init {
        getReviews()
    }

    fun getReviews() {
        viewModelScope.launch {
            showLoading(true)
            val resource = mMovieRepository.getMovieReviews(movieId)
            resource.validateResponse(_reviews, _error)
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getReviews()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieReviewViewModel
    }
}