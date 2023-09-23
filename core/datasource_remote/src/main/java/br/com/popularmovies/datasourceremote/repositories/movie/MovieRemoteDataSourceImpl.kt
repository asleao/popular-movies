package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import br.com.popularmovies.datasourceremote.utils.networkRequest
import br.com.popularmovies.datasourceremoteapi.MovieRemoteDataSource
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTypeParam
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    retrofit: Retrofit,
) : MovieRemoteDataSource {
    //TODO Inject this with dagger
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    override suspend fun getMovies(page: Int, type: MovieTypeParam): List<MovieDto> {
        return mMovieService.getMovies(type.path, page).mapApiResults()
    }

    override suspend fun getMovie(movieId: Long): MovieDto {
        return mMovieService.getMovie(movieId).mapApiResult()
    }

    override suspend fun searchMovies(page: Int, query: String): List<MovieDto> {
        return mMovieService.searchMovies(page, query).mapApiResults()
    }

    override suspend fun getMovieReviews(movieId: Long): List<MovieReviewDto> {
        return mMovieService.getMovieReviews(movieId).mapApiResults()
    }

    override suspend fun getMovieTrailers(movieId: Long): List<MovieTrailerDto> {
        return mMovieService.getMovieTrailers(movieId).mapApiResults()
    }

    override fun getNowPlayingMovies(page: Int): Flow<List<MovieDto>> {
        return networkRequest {
            mMovieService.getMovies(MovieTypeParam.NowPlaying.path, page)
        }.mapApiResults()
    }
}