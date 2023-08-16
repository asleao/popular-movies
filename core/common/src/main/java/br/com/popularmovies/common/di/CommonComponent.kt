package br.com.popularmovies.common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
    (modules = [ViewModelModule::class])
interface CommonComponent : CommonProvider {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CommonComponent
    }
}