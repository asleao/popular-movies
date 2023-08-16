package br.com.popularmovies.home.di

import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.di.DomainComponent
import br.com.popularmovies.home.api.HomeFeatureProvider
import dagger.Component

@Component(
    dependencies = [DomainComponent::class, CommonProvider::class],
    modules = [HomeApiModule::class, HomeViewModelModule::class]
)
@FeatureScope
interface HomeComponent : HomeFeatureProvider