package br.com.popularmovies.home.di

import androidx.lifecycle.ViewModel
import br.com.popularmovies.common.di.ViewModelKey
import br.com.popularmovies.home.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeApiModule