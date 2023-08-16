package  br.com.popularmovies.movie.details.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.di.DomainComponent
import dagger.Component

@Component(
    dependencies = [DomainComponent::class, CommonProvider::class],
)
@FeatureScope
interface MovieDetailsComponent : MovieDetailsFeatureProvider