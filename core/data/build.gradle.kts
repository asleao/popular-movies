plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.repositories
}

dependencies {
    implementation(project(":core:common"))
    api(project(":core:datasource_db")) // TODO check if it is possible to use implementation
    api(project(":core:datasource_remote"))
    implementation(project(":core:model"))

    implementation(libs.bundles.paging)

}