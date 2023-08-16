plugins {
    id("popularmovies.android.feature")
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

//    implementation(libs.androidx.lifecycle.extensions)
}