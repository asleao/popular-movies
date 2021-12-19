package br.com.popularmovies.usecases.movies

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class GetNewestNowPlayingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<Unit, Movie>() {

    override suspend fun build(param: Unit): Result<Movie> {
        return movieRepository.getMostRecentNowPlayingMovie()
    }
}