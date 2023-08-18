package br.com.popularmovies.domain.api.usecases.base

import br.com.popularmovies.common.models.base.Result

interface UseCase<Param, Type> {
    suspend fun build(param: Param): Result<Type>
}