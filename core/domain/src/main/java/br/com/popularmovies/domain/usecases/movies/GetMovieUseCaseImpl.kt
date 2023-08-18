package br.com.popularmovies.domain.usecases.movies

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCaseParams
import br.com.popularmovies.model.movie.Movie
import javax.inject.Inject

class GetMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieUseCase {

    override suspend fun build(param: GetMovieUseCaseParams): Result<Movie> {
        return movieRepository.getMovie(param.movieId)
    }
}