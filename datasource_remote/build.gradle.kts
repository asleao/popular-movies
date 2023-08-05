import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.library")
    id("popularmovies.android.dagger")
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.datasourceRemote

    //TODO Check if its really needed
    buildTypes {
        val props = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "keys.properties")))
        }
        getByName("release") {
            buildConfigField("String", "MdbApiKey", props.getProperty("MdbApiKey"))
        }
        getByName("debug") {
            buildConfigField("String", "MdbApiKey", props.getProperty("MdbApiKey"))
        }
    }
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