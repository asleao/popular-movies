package br.com.popularmovies.domain.usecases.movies

import androidx.paging.PagingData
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.usecases.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: br.com.popularmovies.core.data.api.MovieRepository
) : FlowUseCase<GetMoviesUseCase.Param, PagingData<Movie>>() {

    override fun build(param: Param): Flow<PagingData<Movie>> {
        return movieRepository.getMovies(param.movieType)
    }

    data class Param(
        val movieType: MovieType
    )
}