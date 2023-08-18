package br.com.popularmovies.moviedetails.di

import br.com.popularmovies.model.feature.FeatureApi

interface MovieDetailsFeatureProvider {
    val movieDetailsFeatureApi: FeatureApi
}