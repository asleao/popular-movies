package br.com.popularmovies.moviedetail.trailers.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.R
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.ui.theme.AppTheme

@Composable
fun MovieTrailerCard(movieTrailer: MovieTrailer, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
//        elevation = 2.dp,
        modifier = Modifier
            .width(200.dp)
            .height(150.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_play_circle_outline),
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp, 75.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                movieTrailer.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
private fun MockMovieTrailers() {
    AppTheme {
        MovieTrailerCard(
            movieTrailer =
            MovieTrailer(
                id = "1",
                iso6391 = "",
                iso31661 = "",
                key = "",
                name = "Movie Trailer",
                site = "https://",
                size = "",
                type = ""
            ),
            onClick = {}
        )
    }
}