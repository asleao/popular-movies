package br.com.popularmovies.domain.di

import br.com.popularmovies.data.di.DataModule
import dagger.Module

@Module(
    includes = [
        DataModule::class
    ]
)
interface DomainModule