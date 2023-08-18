package  br.com.popularmovies.movie.details.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.api.DomainComponentProvider
import dagger.Component

@Component(
    dependencies = [br.com.popularmovies.domain.api.DomainComponentProvider::class, CommonProvider::class],
)
@FeatureScope
interface MovieDetailsComponent : MovieDetailsFeatureProvider