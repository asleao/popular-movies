package br.com.popularmovies.home.di

import androidx.fragment.app.FragmentFactory
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.common.di.FeatureScope
import br.com.popularmovies.domain.di.DomainComponent
import br.com.popularmovies.domain.di.DomainComponentProvider
import br.com.popularmovies.home.api.HomeFeatureProvider
import dagger.Component

@Component(
    dependencies = [DomainComponentProvider::class, CommonProvider::class],
)
@FeatureScope
interface HomeComponent : HomeFeatureProvider