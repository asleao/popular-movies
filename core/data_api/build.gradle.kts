plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.coreDataApi
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.jodatime)

    implementation(libs.bundles.paging)
    implementation(libs.bundles.worker)
}