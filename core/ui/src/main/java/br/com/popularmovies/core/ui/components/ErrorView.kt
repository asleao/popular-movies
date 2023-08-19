package br.com.popularmovies.core.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import br.com.popularmovies.core.designsystem.AppTheme

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var imageRes by mutableStateOf<Int?>(null)
    var description by mutableStateOf("")
    var buttonText by mutableStateOf("")
    var buttonClickListener by mutableStateOf<(() -> Unit)?>(null)

    @Composable
    override fun Content() {
        AppTheme {
            ErrorView(
                imageRes = imageRes,
                description = description,
                buttonText = buttonText,
                buttonClickListener = buttonClickListener
            )
        }
    }
}
