@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.coreDatasourceRemote
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

secrets {
    defaultPropertiesFileName = "keys.properties"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:datasource_remote_api"))

    implementation(libs.jodatime)

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.kotlin.codegen)

    api(libs.bundles.network)

    testImplementation(libs.bundles.unittests)
    testImplementation(libs.bundles.uitests)
    testRuntimeOnly(libs.jupiter.engine)
}