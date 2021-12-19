package br.com.popularmovies.usecases.movies

import androidx.paging.PagingData
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : FlowUseCase<Unit, PagingData<Movie>>() {

    override fun build(param: Unit): Flow<PagingData<Movie>> {
        return movieRepository.getPopularMovies()
    }
}