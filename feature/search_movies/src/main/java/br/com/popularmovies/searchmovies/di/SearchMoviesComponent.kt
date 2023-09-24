package  br.com.popularmovies.searchmovies.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.api.DomainComponentProvider
import dagger.Component

@Component(
    dependencies = [DomainComponentProvider::class, CommonProvider::class],
    modules = [SearchMoviesApiModule::class]
)
@FeatureScope
interface SearchMoviesComponent : SearchMoviesFeatureProvider