package br.com.popularmovies.di

import androidx.compose.runtime.compositionLocalOf
import br.com.popularmovies.common.di.CommonProvider
import br.com.popularmovies.domain.api.DomainComponentProvider
import br.com.popularmovies.worker.api.WorkerComponentProvider

interface AppProvider : DomainComponentProvider, CommonProvider, WorkerComponentProvider

val LocalAppProvider = compositionLocalOf<AppProvider> { error("No app provider found!") }
