plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}

android {
    namespace = Namespaces.coreCommon
}

dependencies {
    implementation(libs.androidx.lifecycle.extensions)
}