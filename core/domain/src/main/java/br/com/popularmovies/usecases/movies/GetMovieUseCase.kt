package br.com.popularmovies.usecases.movies

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.repository.MovieRepository
import br.com.popularmovies.usecases.UseCase
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieUseCase.Params, Movie>() {

    override suspend fun build(param: Params): Result<Movie> {
        return movieRepository.getMovie(param.movieId)
    }

    data class Params(
        val movieId: Long
    )
}