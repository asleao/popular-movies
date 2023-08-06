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
    api(project(":core:data"))

    implementation(libs.bundles.paging)
}