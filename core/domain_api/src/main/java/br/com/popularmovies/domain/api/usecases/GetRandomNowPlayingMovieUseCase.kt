package br.com.popularmovies.domain.api.usecases

import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.Movie

interface GetRandomNowPlayingMovieUseCase : FlowUseCase<Unit, List<Movie>>