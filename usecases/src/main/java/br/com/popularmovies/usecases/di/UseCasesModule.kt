package br.com.popularmovies.usecases.di

import br.com.popularmovies.repositories.di.RepositoriesModule
import dagger.Module

@Module(includes = [
    RepositoriesModule::class
])
object UseCasesModule