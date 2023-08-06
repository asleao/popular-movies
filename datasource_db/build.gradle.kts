plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
    id("popularmovies.android.room")
}

android {
    namespace = Namespaces.datasourceDb
}

dependencies {
    implementation(project(":common"))
}