package br.com.popularmovies.usecases.movies

import androidx.paging.PagingData
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieType
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : FlowUseCase<GetMoviesUseCase.Param, PagingData<Movie>>() {

    override fun build(param: Param): Flow<PagingData<Movie>> {
        return movieRepository.getMovies(param.movieType)
    }

    data class Param(
        val movieType: MovieType
    )
}