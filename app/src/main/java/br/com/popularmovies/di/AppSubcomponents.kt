package br.com.popularmovies.di

import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import dagger.Module


@Module(
    subcomponents = [
        MovieDetailComponent::class
    ]
)
class AppSubcomponents