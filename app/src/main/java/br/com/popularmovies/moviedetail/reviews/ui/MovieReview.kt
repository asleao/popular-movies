package br.com.popularmovies.moviedetail.reviews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.entities.movie.MovieReview
import br.com.popularmovies.ui.theme.AppTheme
import br.com.popularmovies.ui.theme.OpensSansTypography

@Composable
fun MovieReview(movieReview: MovieReview) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
    ) {
        Text(
            movieReview.author,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            style = OpensSansTypography.h2,
            color = MaterialTheme.colors.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            movieReview.content,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.onPrimary
        )
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
            )
        )
    }
}