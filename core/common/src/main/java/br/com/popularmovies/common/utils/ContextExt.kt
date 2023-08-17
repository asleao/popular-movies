package br.com.popularmovies.common.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import br.com.popularmovies.common.Constants

fun Context.youtube(key: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("${Constants.YOUTUBE_URL}$key")
    )
    try {
        startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        startActivity(webIntent)
    }
}