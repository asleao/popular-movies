package br.com.popularmovies.domain.api.usecases.base

interface UseCase<Param, Type> {
    suspend fun build(param: Param): Type
}