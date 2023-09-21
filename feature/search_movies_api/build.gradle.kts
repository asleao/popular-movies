plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.featureSearchMoviesApi
}

dependencies {
    implementation(project(":core:model"))
}