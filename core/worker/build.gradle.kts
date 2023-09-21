plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
}


android {
    namespace = Namespaces.coreWorker
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:datasource_remote_api"))
    implementation(project(":core:datasource_db_api"))
    implementation(project(":core:worker_api"))

    implementation(libs.bundles.worker)
}