package br.com.popularmovies.moviedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCaseParams
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieDetailViewModel @AssistedInject constructor(
    getMovieUseCase: GetMovieUseCase,
    getMovieTrailersUseCase: GetMovieTrailersUseCase,
    getMovieReviewsUseCase: GetMovieReviewsUseCase,
    @Assisted private val movieId: Long
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Long): MovieDetailViewModel
    }

    val movieUiState: Flow<MovieUiState> = getMovieUseCase.build(GetMovieUseCaseParams(movieId))
        .map(MovieUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieUiState.Loading
        )

    val trailersUiState = getMovieTrailersUseCase.build(GetMovieTrailersUseCaseParams(movieId))
        .map(TrailerUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrailerUiState.Loading
        )

    val reviewsUiState = getMovieReviewsUseCase.build(GetMovieReviewsUseCaseParams(movieId))
        .map(ReviewUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ReviewUiState.Loading
        )

    private val _playTrailer = MutableLiveData<String>()
    val playTrailer: LiveData<String>
        get() = _playTrailer


    fun playTrailer(key: String) {
        _playTrailer.value = key
    }
}

//TODO move this to ui module
sealed interface MovieUiState {
    object Loading : MovieUiState

    data class Success(val movie: Movie) : MovieUiState
}


sealed interface ReviewUiState {
    object Loading : ReviewUiState

    data class Success(val reviews: List<MovieReview>) : ReviewUiState
}


sealed interface TrailerUiState {
    object Loading : TrailerUiState

    data class Success(val trailers: List<MovieTrailer>) : TrailerUiState
}