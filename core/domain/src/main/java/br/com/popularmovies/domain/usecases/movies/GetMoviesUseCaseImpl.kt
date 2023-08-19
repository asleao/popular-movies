package br.com.popularmovies.domain.usecases.movies

import androidx.paging.PagingData
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCaseParams
import br.com.popularmovies.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesUseCase {

    override fun build(params: GetMoviesUseCaseParams): Flow<PagingData<Movie>> {
        return movieRepository.getMovies(params.movieType)
    }
}