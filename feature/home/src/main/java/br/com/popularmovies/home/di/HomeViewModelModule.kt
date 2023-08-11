package br.com.popularmovies.home.di

import androidx.lifecycle.ViewModel
import br.com.popularmovies.home.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class HomeViewModelModule {
    @Binds
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}