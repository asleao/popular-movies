package br.com.popularmovies.searchmovies.di

import br.com.popularmovies.searchmovies.api.SearchMoviesFeatureApi

interface SearchMoviesFeatureProvider {
    val searchMoviesFeatureApi: SearchMoviesFeatureApi
}