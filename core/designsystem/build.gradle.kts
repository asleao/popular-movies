plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.library.compose")
}

android {
    namespace = Namespaces.coreDesignSystem
}

dependencies {
    implementation(project(":core:common"))
}