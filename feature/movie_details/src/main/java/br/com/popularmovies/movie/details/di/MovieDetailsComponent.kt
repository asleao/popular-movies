package  br.com.popularmovies.movie.details.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.di.DomainComponentProvider
import dagger.Component

@Component(
    dependencies = [DomainComponentProvider::class, CommonProvider::class],
)
@FeatureScope
interface MovieDetailsComponent : MovieDetailsFeatureProvider