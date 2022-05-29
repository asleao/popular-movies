package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieType
import br.com.popularmovies.usecases.movies.GetMoviesUseCase
import br.com.popularmovies.usecases.movies.GetRandomNowPlayingMovieUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase,
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val loading = MutableLiveData(false)

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _randomNowPlayingMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    val randomNowPlayingMovie: LiveData<List<Movie>>
        get() = _randomNowPlayingMovie

    val popularMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCase.Param(MovieType.MostPopular))
            .cachedIn(viewModelScope)

    val nowPlayingMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCase.Param(MovieType.NowPlaying))
            .cachedIn(viewModelScope)

    val topHatedMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCase.Param(MovieType.TopRated))
            .cachedIn(viewModelScope)

    init {
        getNewestNowPlayingMovie()
    }

    private fun getNewestNowPlayingMovie() = viewModelScope.launch {
        when (val result = getRandomNowPlayingMovieUseCase.build(Unit)) {
            is Result.Success -> _randomNowPlayingMovie.value = result.data
            is Result.Error -> {
                _error.value = result.error
            }
        }
    }

}