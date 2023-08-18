package br.com.popularmovies.domain.di

import br.com.popularmovies.core.data.api.DataComponentProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [DataComponentProvider::class]
)
@Singleton
interface DomainComponent : DomainComponentProvider
