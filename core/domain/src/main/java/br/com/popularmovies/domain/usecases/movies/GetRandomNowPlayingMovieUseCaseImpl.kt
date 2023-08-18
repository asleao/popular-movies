package br.com.popularmovies.domain.usecases.movies

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.model.movie.Movie
import javax.inject.Inject

class GetRandomNowPlayingMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetRandomNowPlayingMovieUseCase {

    override suspend fun build(param: Unit): Result<List<Movie>> {
        return movieRepository.getRandomNowPlayingMovies()
    }
}