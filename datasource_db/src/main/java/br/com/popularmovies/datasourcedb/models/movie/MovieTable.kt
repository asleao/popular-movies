package br.com.popularmovies.datasourcedb.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.LocalDate
import java.math.BigDecimal

sealed class MovieTable {
    abstract var id: Long
    abstract var remoteId: Long
    abstract val voteAverage: BigDecimal
    abstract val originalTitle: String
    abstract val popularity: BigDecimal
    abstract val poster: String
    abstract val overview: String
    abstract val releaseDate: LocalDate
    abstract val votes: Int
    abstract var isFavorite: Boolean

    @Entity(tableName = "movie_popular")
    data class MostPopular(
        @PrimaryKey(autoGenerate = true)
        override var id: Long = 0,
        override var remoteId: Long = 0,
        override val voteAverage: BigDecimal,
        override val originalTitle: String,
        override val popularity: BigDecimal,
        override val poster: String,
        override val overview: String,
        override val releaseDate: LocalDate,
        override val votes: Int,
        override var isFavorite: Boolean = false
    ) : MovieTable()

    @Entity(tableName = "movie_top_rated")
    data class TopRated(
        @PrimaryKey(autoGenerate = true)
        override var id: Long = 0,
        override var remoteId: Long = 0,
        override val voteAverage: BigDecimal,
        override val originalTitle: String,
        override val popularity: BigDecimal,
        override val poster: String,
        override val overview: String,
        override val releaseDate: LocalDate,
        override val votes: Int,
        override var isFavorite: Boolean = false
    ) : MovieTable()

    @Entity(tableName = "movie_now_playing")
    data class NowPlaying(
        @PrimaryKey(autoGenerate = true)
        override var id: Long = 0,
        override var remoteId: Long = 0,
        override val voteAverage: BigDecimal,
        override val originalTitle: String,
        override val popularity: BigDecimal,
        override val poster: String,
        override val overview: String,
        override val releaseDate: LocalDate,
        override val votes: Int,
        override var isFavorite: Boolean = false
    ) : MovieTable()
}

