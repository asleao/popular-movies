package br.com.popularmovies.entities.usecase

abstract class UseCase<Param, Result> {
    abstract suspend fun build(param: Param): Result
}