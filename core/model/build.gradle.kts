plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.entities
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.unittests)
}