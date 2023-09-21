package  br.com.popularmovies.searchmovies.di

import androidx.fragment.app.Fragment
import br.com.popularmovies.common.di.FragmentKey
import br.com.popularmovies.searchmovies.ui.SearchMoviesFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchMoviesFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(SearchMoviesFragment::class)
    abstract fun bindSearchMoviesFragment(searchMoviesFragment: SearchMoviesFragment): Fragment
}