package  br.com.popularmovies.movie.details.di

import androidx.lifecycle.ViewModelProvider
import br.com.popularmovies.common.di.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsViewModelModule {
//    @Binds
//    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(MovieViewModel::class)
//    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}