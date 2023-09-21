plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreWorkerApi
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.bundles.worker)
}