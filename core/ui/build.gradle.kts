plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.library.compose")
}

android {
    namespace = Namespaces.coreUi

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.bundles.ui)
    implementation(libs.androidx.lifecycle.livedata.ktx)
}