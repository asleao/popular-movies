package br.com.popularmovies.datasourcedb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.typeconverters.BigDecimalConverter
import br.com.popularmovies.datasourcedb.typeconverters.LocalDateConverter

@Database(entities = [MovieTable::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class, BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): br.com.popularmovies.datasourcedb.daos.MovieDao
}