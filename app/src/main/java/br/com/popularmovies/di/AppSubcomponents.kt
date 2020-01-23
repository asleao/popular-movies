package br.com.popularmovies.di

import br.com.popularmovies.di.subcomponents.MovieComponent
import br.com.popularmovies.di.subcomponents.MovieDetailComponent
import dagger.Module


@Module(subcomponents = [MovieComponent::class, MovieDetailComponent::class])
class AppSubcomponents