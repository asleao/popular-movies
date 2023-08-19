package br.com.popularmovies.domain.api.usecases.base

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Param, Type> {
    fun build(param: Param): Flow<Type>
}