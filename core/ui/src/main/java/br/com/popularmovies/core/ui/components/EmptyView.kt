package br.com.popularmovies.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview

@Composable
fun EmptyView(modifier: Modifier, message: String, imageVector: ImageVector) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = imageVector,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .clip(CircleShape),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = message,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@ThemePreview
@Composable
fun EmptyViewPreview() {
    AppTheme {
        EmptyView(
            modifier = Modifier,
            message = "No data",
            imageVector = Icons.Filled.List
        )
    }
}