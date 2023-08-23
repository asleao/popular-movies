package br.com.popularmovies.domain.usecases.movies

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomNowPlayingMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetRandomNowPlayingMovieUseCase {

    override fun build(param: Unit): Flow<List<Movie>> {
        return movieRepository.getRandomNowPlayingMovies()
    }
}