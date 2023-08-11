package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    retrofit: Retrofit,
    private val retrofitResponse: RetrofitResponse
) : MovieRemoteDataSource {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    override suspend fun getMovies(page: Int, type: MovieTypeParam): Result<List<MovieDto>> {
        return retrofitResponse
            .request {
                mMovieService.getMovies(type.path, page)
            }
            .mapApiResults()
    }

    override suspend fun getMovie(movieId: Long): Result<MovieDto> {
        return retrofitResponse
            .request { mMovieService.getMovie(movieId) }
            .mapApiResult()
    }

    override suspend fun getMovieReviews(movieId: Long): Result<List<MovieReviewDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieReviews(movieId) }
            .mapApiResults()
    }

    override suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailerDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieTrailers(movieId) }
            .mapApiResults()
    }

    override suspend fun getNowPlayingMovies(page: Int): Result<List<MovieDto>> {
        return retrofitResponse
            .request { mMovieService.getMovies(MovieTypeParam.NowPlaying.path, page) }
            .mapApiResults()
    }
}