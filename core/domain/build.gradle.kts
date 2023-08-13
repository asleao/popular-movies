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
    implementation(project(":core:datasource_remote_api")) //TODO check this
    implementation(project(":core:datasource_db_api")) //TODO check this
    api(project(":core:data"))

    implementation(libs.bundles.paging)
}