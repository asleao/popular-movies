package br.com.popularmovies.datasourcedb.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [DatabaseModule::class, DatabaseRepositoryModule::class]
)
//@Singleton
interface DatabaseComponent : DatabaseComponentProvider {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DatabaseComponent
    }
}
