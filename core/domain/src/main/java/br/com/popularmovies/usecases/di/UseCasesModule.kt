package br.com.popularmovies.usecases.di

import br.com.popularmovies.data.di.DataModule
import dagger.Module

@Module(includes = [
    DataModule::class
])
object UseCasesModule