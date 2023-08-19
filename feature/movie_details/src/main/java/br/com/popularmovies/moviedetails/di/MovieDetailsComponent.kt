package  br.com.popularmovies.moviedetails.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.api.DomainComponentProvider
import dagger.Component

@Component(
    dependencies = [DomainComponentProvider::class, CommonProvider::class],
    modules = [MovieDetailsApiModule::class]
)
@FeatureScope
interface MovieDetailsComponent : MovieDetailsFeatureProvider