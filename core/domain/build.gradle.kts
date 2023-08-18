plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.coreDomain
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:data_api"))

    implementation(libs.bundles.paging)
}