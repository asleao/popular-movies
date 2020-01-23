package br.com.popularmovies.di

import br.com.popularmovies.di.subcomponents.MovieComponent
import dagger.Module


@Module(subcomponents = [MovieComponent::class])
class AppSubcomponents