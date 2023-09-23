package br.com.popularmovies.core.ui.components.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.popularmovies.common.Constants
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.joda.time.LocalDate
import java.math.BigDecimal

@Composable
fun MovieCard(modifier: Modifier = Modifier, movie: Movie, onMovieSelected: (Movie) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onMovieSelected(movie)
            }
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(1.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (movie.poster == null) {
                Text(
                    text = movie.originalTitle,
                    style = MaterialTheme.typography.headlineLarge
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.IMAGE_URL + movie.poster)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun moviePreview() {
    AppTheme {
        val movie = Movie(
            votes = 1,
            id = 1L,
            type = MovieType.MostPopular,
            voteAverage = BigDecimal.TEN,
            originalTitle = "Movie Title",
            popularity = BigDecimal.TEN,
            poster = null,
            overview = "",
            releaseDate = LocalDate.now()
        )
        MovieCard(Modifier, movie) {}
    }
}
