plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.entities
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.unittests)
}