package br.com.popularmovies.di

import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
}