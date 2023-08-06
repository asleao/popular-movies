package br.com.popularmovies.usecases

import br.com.popularmovies.common.models.base.Result

abstract class UseCase<Param, Type> {
    abstract suspend fun build(param: Param): Result<Type>
}