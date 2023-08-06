plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreCommon
}

dependencies {
    api(libs.jodatime)
    api(libs.kotlinx.coroutines.android)
}