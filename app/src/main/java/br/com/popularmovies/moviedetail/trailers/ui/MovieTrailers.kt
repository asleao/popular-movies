package br.com.popularmovies.moviedetail.trailers.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.R
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.ui.theme.AppTheme

@Composable
fun MovieTrailers(movieTrailers: List<MovieTrailer>) {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
        val listState = rememberLazyListState()

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(end = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movieTrailers) { movieTrailer ->
                MovieTrailer(
                    movieTrailer,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .align(Alignment.Center)
                        .background(color = MaterialTheme.colors.surface)
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
        Image(
            painter = painterResource(R.drawable.ic_play_circle_outline),
            contentDescription = null,
            modifier = Modifier
                .height(75.dp)
                .width(75.dp)
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            movieTrailer.name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun MockMovieTrailers() {
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