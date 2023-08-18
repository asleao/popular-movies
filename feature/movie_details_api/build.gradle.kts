plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.featureMovieDetailsApi
}

dependencies {
    implementation(project(":core:model"))
}