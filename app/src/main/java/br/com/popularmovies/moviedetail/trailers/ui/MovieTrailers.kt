package br.com.popularmovies.moviedetail.trailers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.ui.theme.AppTheme
import br.com.popularmovies.ui.theme.OpensSansTypography


@Composable
fun MovieTrailers(movieTrailers: List<MovieTrailer>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
    ) {
        val listState = rememberLazyListState()

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(end = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movieTrailers) { movieTrailers ->
                MovieTrailer(
                    movieTrailers,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .background(color = MaterialTheme.colors.background)
                )
            }
        }
    }
}

@Composable
fun MovieTrailer(movieTrailer: MovieTrailer, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            movieTrailer.name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp),
            style = OpensSansTypography.subtitle1,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun MockMovieReview() {
    AppTheme {
        MovieTrailers(
            movieTrailers = listOf(
                MovieTrailer(
                    id = "1",
                    iso6391 = "",
                    iso31661 = "",
                    key = "",
                    name = "Movie Trailer",
                    site = "https://",
                    size = "",
                    type = ""
                )
            )
        )
    }
}