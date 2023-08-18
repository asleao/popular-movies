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
    implementation(project(":feature:home_api"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain")) //TODO check this

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.bundles.paging)
}