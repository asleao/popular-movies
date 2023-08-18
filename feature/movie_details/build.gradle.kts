plugins {
    id("popularmovies.android.feature")
    id("popularmovies.android.library.compose")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = Namespaces.featureMovieDetails

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":feature:movie_details_api"))
    implementation(project(":core:domain_api"))
    implementation(project(":core:ui"))
    implementation(libs.jodatime)
    implementation(libs.androidx.navigation.ui.ktx)
}