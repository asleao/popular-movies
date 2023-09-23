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
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.jodatime)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.paging)
    implementation(libs.androidx.lifecycle.livedata.ktx)
}