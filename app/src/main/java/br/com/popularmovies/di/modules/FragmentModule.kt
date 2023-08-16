package br.com.popularmovies.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import br.com.popularmovies.common.di.FragmentKey
import br.com.popularmovies.home.ui.MovieFragment
import br.com.popularmovies.home.ui.NowPlayingMovieFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @Binds
    abstract fun bindFragmentFactory(factory: FragmentFactoryImpl): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(MovieFragment::class)
    abstract fun bindMovieFragment(movieFragment: MovieFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(NowPlayingMovieFragment::class)
    abstract fun bindNowPlayingMovieFragment(movieFragment: NowPlayingMovieFragment): Fragment
}