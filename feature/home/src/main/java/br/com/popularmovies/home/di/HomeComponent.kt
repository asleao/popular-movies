package br.com.popularmovies.home.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.api.DomainComponentProvider
import br.com.popularmovies.home.api.HomeFeatureProvider
import dagger.Component

@Component(
    dependencies = [DomainComponentProvider::class, CommonProvider::class],
    modules = [HomeApiModule::class]
)
@FeatureScope
interface HomeComponent : HomeFeatureProvider