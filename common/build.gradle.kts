plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.common
}

dependencies {
    api(libs.jodatime)
    api(libs.kotlinx.coroutines.android)
}