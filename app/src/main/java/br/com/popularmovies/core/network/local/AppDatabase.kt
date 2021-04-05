package br.com.popularmovies.core.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.popularmovies.core.network.local.typeconverters.BigDecimalConverter
import br.com.popularmovies.core.network.local.typeconverters.LocalDateConverter
import br.com.popularmovies.services.movieService.response.MovieTable
import br.com.popularmovies.services.movieService.source.local.MovieDao

@Database(entities = [MovieTable::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class, BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
