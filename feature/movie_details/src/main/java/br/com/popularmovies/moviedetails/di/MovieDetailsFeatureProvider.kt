package br.com.popularmovies.moviedetails.di

import br.com.popularmovies.feature.moviedetails.api.MovieDetailsFeatureApi

interface MovieDetailsFeatureProvider {
    val movieDetailsFeatureApi: MovieDetailsFeatureApi
}