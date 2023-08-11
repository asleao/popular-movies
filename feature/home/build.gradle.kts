plugins {
    id("popularmovies.android.feature")
}

android {
    namespace = Namespaces.featureHome

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.bundles.paging)
}