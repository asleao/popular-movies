plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}

android {
    namespace = Namespaces.coreCommon

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.bundles.worker)
}