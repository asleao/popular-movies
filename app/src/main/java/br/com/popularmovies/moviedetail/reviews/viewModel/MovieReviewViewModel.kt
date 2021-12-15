package br.com.popularmovies.moviedetail.reviews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.usecases.movies.reviews.GetMovieReviewsUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieReviewViewModel @AssistedInject constructor(
    val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    @Assisted val movieId: Long
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Long): MovieReviewViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error
    private val _reviews = MutableLiveData<List<MovieReview>>()
    val reviews: LiveData<List<MovieReview>>
        get() = _reviews

    init {
        getReviews()
    }

    fun getReviews() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieReviewsUseCase.Params(movieId)
            when (val result = getMovieReviewsUseCase.build(params)) {
                is Result.Success -> _reviews.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getReviews()
    }
}
