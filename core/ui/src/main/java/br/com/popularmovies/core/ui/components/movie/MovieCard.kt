package br.com.popularmovies.core.ui.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
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
    ) {
        Column(modifier = Modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_URL + movie.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1.5f)
            )
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
            poster = "",
            overview = "",
            releaseDate = LocalDate.now()
        )
        MovieCard(Modifier, movie) {}
    }
}
