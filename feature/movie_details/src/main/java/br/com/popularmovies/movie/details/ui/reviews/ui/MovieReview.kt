package br.com.popularmovies.movie.details.ui.reviews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.OpensSansTypography

@Composable
fun MovieReview(movieReview: MovieReview, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
//        elevation = 2.dp,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        Column {
            Text(
                movieReview.author,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp),
                style = OpensSansTypography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                movieReview.content,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

@Preview
@Composable
private fun MockMovieReview() {
    AppTheme {
        MovieReview(
            movieReview = MovieReview(
                author = "Teste",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
            ), {}
        )
    }
}