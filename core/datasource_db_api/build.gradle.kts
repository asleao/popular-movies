plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreDatasourceDbApi
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.bundles.room)
}