plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.featureHomeApi
}

dependencies {
    implementation(project(":core:model"))
}