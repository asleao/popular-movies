buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin) //TODO Move to plugin somehow
    }

}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}