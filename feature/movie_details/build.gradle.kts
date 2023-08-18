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
    implementation(project(":core:domain_api"))
    implementation(project(":core:ui"))
    implementation(libs.jodatime)
}