plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
    id("popularmovies.android.room")
}

android {
    namespace = Namespaces.coreDatasourceDb
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:datasource_db_api"))
    implementation(libs.jodatime)
}