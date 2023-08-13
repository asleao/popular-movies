package br.com.popularmovies.datasourcedb.di

import android.content.Context
import androidx.room.Room
import br.com.popularmovies.datasourcedb.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "popularmovies"
        ).build()
    }
}