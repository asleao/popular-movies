package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.entities.movie.MovieReview

fun MovieReviewDto.toDomain(): MovieReview {
    return MovieReview(
            author = author,
            content = content
    )
}
