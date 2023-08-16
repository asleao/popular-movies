package br.com.popularmovies.di

import androidx.compose.runtime.compositionLocalOf
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.domain.di.DomainComponentProvider

interface AppProvider : DomainComponentProvider, CommonProvider

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }
