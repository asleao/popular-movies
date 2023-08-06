plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreModel
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.bundles.paging)

    testImplementation(libs.bundles.unittests)
}