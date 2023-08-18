package br.com.popularmovies.home.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.api.DomainComponentProvider
import br.com.popularmovies.home.api.HomeFeatureProvider
import dagger.Component

@Component(
    dependencies = [br.com.popularmovies.domain.api.DomainComponentProvider::class, CommonProvider::class],
)
@FeatureScope
interface HomeComponent : HomeFeatureProvider