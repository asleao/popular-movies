plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}

android {
    namespace = Namespaces.coreCommon
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.extensions)
}