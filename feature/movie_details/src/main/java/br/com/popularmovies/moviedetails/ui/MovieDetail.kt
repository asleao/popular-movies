package br.com.popularmovies.moviedetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.common.Constants.IMAGE_URL
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.joda.time.LocalDate
import java.math.BigDecimal

@Composable
fun MovieDetail(movie: Movie) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMAGE_URL + movie.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1.5f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = movie.originalTitle,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            movie.overview,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            AssistChip(
                onClick = { /* Do something! */ },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = MaterialTheme.colorScheme.onSecondary
                ),
                label = {
                    Text(
                        movie.releaseDate.year.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            AssistChip(
                onClick = { /* Do something! */ },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = MaterialTheme.colorScheme.onSecondary
                ),
                label = {
                    Text(
                        "TMDb ${movie.voteAverage}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun MockMovieDetail() {
    br.com.popularmovies.core.designsystem.AppTheme {
        MovieDetail(
            movie = Movie(
                originalTitle = "Teste",
                votes = 1,
                id = 1,
                type = MovieType.MostPopular,
                voteAverage = BigDecimal.TEN,
                popularity = BigDecimal.ONE,
                poster = "",
                overview = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                releaseDate = LocalDate.now(),
                isFavorite = false
            )
        )
    }
}