package br.com.popularmovies.home.di

import br.com.popularmovies.common.di.FeatureScope
import dagger.Component

@Component(modules = [HomeViewModelModule::class])
@FeatureScope
interface HomeComponent {

    @Component.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}