plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.usecases
}

dependencies {
    implementation(project(":common"))
    implementation(project(":entities"))
    api(project(":repositories"))

    implementation(libs.bundles.paging)
}