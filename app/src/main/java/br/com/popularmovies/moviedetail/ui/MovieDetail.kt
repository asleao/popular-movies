package br.com.popularmovies.moviedetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.ui.theme.AppTheme
import br.com.popularmovies.ui.theme.typography
import com.google.accompanist.glide.rememberGlidePainter
import org.joda.time.LocalDate
import java.math.BigDecimal
import br.com.popularmovies.R

@Composable
fun MovieDetail(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
    ) {
        Image(
            painter = rememberGlidePainter(
                request = movie.poster,
                previewPlaceholder = R.drawable.loading,
                fadeIn = true
            ),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            movie.originalTitle,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            style = typography.h6,
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            movie.overview,
            style = typography.body2,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                movie.releaseDate.year.toString(),
                modifier = Modifier
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                style = typography.caption,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "TMDb ${movie.voteAverage}",
                fontWeight = FontWeight.Bold,
                style = typography.caption,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun MockMovieDetail() {
    AppTheme {
        MovieDetail(
            movie = Movie(
                originalTitle = "Teste",
                votes = 1,
                id = 1,
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