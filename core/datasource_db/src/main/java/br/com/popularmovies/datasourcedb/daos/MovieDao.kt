package br.com.popularmovies.datasourcedb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.core.api.models.relations.MovieReviewsRelation
import br.com.popularmovies.core.api.models.reviews.ReviewTable
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE type = :type")
    fun movies(type: MovieTypeTable): PagingSource<Int, MovieTable>

    @Query("SELECT * FROM movies WHERE type = :type")
    fun moviesList(type: MovieTypeTable): Flow<List<MovieTable>>

    @Query("SELECT DISTINCT * FROM movies WHERE remoteId = :movieId")
    fun getMovie(movieId: Long): MovieTable?

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(movieReviews: List<ReviewTable>)

    @Transaction
    @Query("SELECT * FROM movies WHERE remoteId = :movieRemoteId")
    suspend fun getMovieReviews(movieRemoteId: Long): MovieReviewsRelation

    @Transaction
    @Query("DELETE FROM reviews WHERE movieId = :movieId")
    suspend fun deleteMovieReviews(movieId: Long)
}
