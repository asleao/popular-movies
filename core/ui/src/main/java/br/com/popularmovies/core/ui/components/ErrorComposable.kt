package br.com.popularmovies.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.popularmovies.core.ui.R

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
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                style = MaterialTheme.typography.titleMedium,
                text = desc,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { buttonClickListener?.invoke() }) {
            buttonText?.let { text ->
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    text = text,
                    color = MaterialTheme.colorScheme.primary
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