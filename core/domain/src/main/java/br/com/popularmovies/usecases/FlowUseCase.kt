package br.com.popularmovies.usecases

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<Param, Type> {
    abstract fun build(param: Param): Flow<Type>
}