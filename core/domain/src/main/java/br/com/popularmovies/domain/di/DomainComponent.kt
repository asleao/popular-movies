package br.com.popularmovies.domain.di

import br.com.popularmovies.core.data.api.DataComponentProvider
import br.com.popularmovies.domain.api.DomainComponentProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [DataComponentProvider::class],
    modules = [DomainModule::class]
)
@Singleton
interface DomainComponent : DomainComponentProvider
