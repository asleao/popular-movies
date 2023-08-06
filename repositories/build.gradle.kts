plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.repositories
}

dependencies {
    implementation(project(":common"))
    api(project(":datasource_db")) // TODO check if it is possible to use implementation
    api(project(":datasource_remote"))
    implementation(project(":entities"))

    implementation(libs.bundles.paging)

}