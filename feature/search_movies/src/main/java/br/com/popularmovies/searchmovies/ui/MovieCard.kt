package br.com.popularmovies.searchmovies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import org.joda.time.LocalDate
import java.math.BigDecimal

@Composable
fun MovieCard(movie: Movie, onMovieSelected: (Movie) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = br.com.popularmovies.core.ui.R.drawable.ic_cloud_off),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie"
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = movie.originalTitle,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = "${movie.releaseDate}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "${movie.releaseDate}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
        MovieCard(movie) {}
    }
}
