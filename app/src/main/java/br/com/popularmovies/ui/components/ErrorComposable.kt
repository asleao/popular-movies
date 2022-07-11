package br.com.popularmovies.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int?,
    description: String?,
    buttonText: String?,
    buttonClickListener: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        imageRes?.let { resource ->
            Image(
                painterResource(id = resource),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        description?.let { desc ->
            Text(
                modifier = Modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.subtitle1,
                text = desc,
                color = MaterialTheme.colors.onPrimary
            )

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { buttonClickListener?.invoke() }) {
            buttonText?.let { text ->
                Text(
                    style = MaterialTheme.typography.caption,
                    text = text,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NoConnectionErrorPreview() {
    ErrorView(
        imageRes = R.drawable.ic_cloud_off,
        description = "No Internet Connection",
        buttonText = "Try Again",
        buttonClickListener = null
    )
}