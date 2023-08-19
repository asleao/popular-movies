package  br.com.popularmovies.moviedetails.di

import androidx.fragment.app.Fragment
import br.com.popularmovies.common.di.FragmentKey
import br.com.popularmovies.moviedetails.ui.MovieDetailFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsFragmentModule {

    @Binds
    @IntoMap
    @FragmentKey(MovieDetailFragment::class)
    abstract fun bindMovieDetailFragment(movieDetailFragment: MovieDetailFragment): Fragment
}