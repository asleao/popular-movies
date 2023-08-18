package br.com.popularmovies.di

import androidx.compose.runtime.compositionLocalOf
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.domain.api.DomainComponentProvider

interface AppProvider : br.com.popularmovies.domain.api.DomainComponentProvider, CommonProvider

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }
