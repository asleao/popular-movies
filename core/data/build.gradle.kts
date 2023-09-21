plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.coreData
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:datasource_remote_api"))
    implementation(project(":core:datasource_db_api"))
    implementation(project(":core:data_api"))
    implementation(project(":core:model"))

    implementation(libs.jodatime)

    implementation(libs.bundles.paging)
    implementation(libs.bundles.worker)
}