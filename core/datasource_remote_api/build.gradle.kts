plugins {
    id("popularmovies.android.library")
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.coreDatasourceRemoteApi
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.bundles.moshi)
    ksp(libs.moshi.kotlin.codegen)
}