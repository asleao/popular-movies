package br.com.popularmovies.datasourcedb.di

import android.content.Context
import br.com.popularmovies.core.api.DatabaseComponentProvider
import dagger.BindsInstance
import dagger.Component

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
