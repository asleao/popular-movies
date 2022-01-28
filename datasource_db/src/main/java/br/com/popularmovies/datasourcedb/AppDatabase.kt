package br.com.popularmovies.datasourcedb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.popularmovies.datasourcedb.daos.MovieDao
import br.com.popularmovies.datasourcedb.daos.RemoteKeysDao
import br.com.popularmovies.datasourcedb.models.keys.RemoteKeyTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.typeconverters.BigDecimalConverter
import br.com.popularmovies.datasourcedb.typeconverters.LocalDateConverter
import br.com.popularmovies.datasourcedb.typeconverters.MovieTypeConverters

@Database(
    entities = [MovieTable.MostPopular::class,
        MovieTable.TopRated::class,
        MovieTable.NowPlaying::class,
        RemoteKeyTable::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class, BigDecimalConverter::class, MovieTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}