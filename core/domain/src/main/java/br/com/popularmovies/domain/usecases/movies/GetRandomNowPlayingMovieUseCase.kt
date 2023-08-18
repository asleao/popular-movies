package br.com.popularmovies.domain.usecases.movies

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.usecases.UseCase
import javax.inject.Inject

class GetRandomNowPlayingMovieUseCase @Inject constructor(
    private val movieRepository: br.com.popularmovies.core.data.api.MovieRepository
) : UseCase<Unit, List<Movie>>() {

    override suspend fun build(param: Unit): Result<List<Movie>> {
        return movieRepository.getRandomNowPlayingMovies()
    }
}