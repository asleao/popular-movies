plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}

android {
    namespace = Namespaces.coreCommon
}

dependencies {
    api(libs.jodatime)
    api(libs.kotlinx.coroutines.android)
}