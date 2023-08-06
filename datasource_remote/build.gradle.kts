@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
    alias(libs.plugins.ksp)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = Namespaces.datasourceRemote
}

secrets {
    defaultPropertiesFileName = "keys.properties"
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.kotlin.codegen)

    api(libs.bundles.network)

    testImplementation(libs.bundles.unittests)
    testImplementation(libs.bundles.uitests)
    testRuntimeOnly(libs.jupiter.engine)
}