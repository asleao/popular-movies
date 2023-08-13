package br.com.popularmovies.di

import androidx.compose.runtime.compositionLocalOf

interface AppProvider

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }
