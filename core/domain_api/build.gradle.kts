plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreDomainApi
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.bundles.paging)
}