package br.com.popularmovies.moviedetail.reviews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.Error
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.MovieReview
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
    private val _reviews = MutableLiveData<List<MovieReview>>()
    val reviews: LiveData<List<MovieReview>>
        get() = _reviews

    init {
        getReviews()
    }

    fun getReviews() {
        viewModelScope.launch {
            showLoading(true)
            when (val result = mMovieRepository.getMovieReviews(movieId)) {
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

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieReviewViewModel
    }
}
