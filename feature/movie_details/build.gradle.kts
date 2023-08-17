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
    implementation(project(":core:datasource_db_api")) //TODO check this
    implementation(project(":core:datasource_remote_api")) //TODO check this
    implementation(project(":core:ui"))
    implementation(libs.jodatime)
}