package br.com.popularmovies.datasourcedb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.relations.MovieWithReviewsRelation
import br.com.popularmovies.core.api.models.relations.MovieWithTrailersRelation
import br.com.popularmovies.core.api.models.review.ReviewTable
import br.com.popularmovies.core.api.models.trailer.TrailerTable
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE type = :type")
    fun movies(type: MovieTypeTable): PagingSource<Int, MovieTable>

    @Query("SELECT * FROM movies WHERE type = :type")
    fun moviesList(type: MovieTypeTable): Flow<List<MovieTable>>

    @Query("SELECT DISTINCT * FROM movies WHERE remoteId = :movieId")
    fun getMovie(movieId: Long): MovieTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieTables: List<MovieTable>)

    @Transaction
    @Query("DELETE FROM movies WHERE type = :type")
    suspend fun deleteAllMovies(type: MovieTypeTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieTable: MovieTable)

    @Transaction
    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Long)

    @Transaction
    @Query("SELECT * FROM movies WHERE remoteId = :movieRemoteId")
    suspend fun getMovieReviews(movieRemoteId: Long): MovieWithReviewsRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(movieReviews: List<ReviewTable>)

    @Transaction
    @Query("DELETE FROM reviews WHERE movieId = :movieId")
    suspend fun deleteMovieReviews(movieId: Long)

    @Transaction
    @Query("SELECT * FROM movies WHERE remoteId = :movieRemoteId")
    suspend fun getMovieTrailers(movieRemoteId: Long): MovieWithTrailersRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrailers(movieTrailers: List<TrailerTable>)

    @Transaction
    @Query("DELETE FROM trailers WHERE movieId = :movieId")
    suspend fun deleteMovieTrailers(movieId: Long)
}