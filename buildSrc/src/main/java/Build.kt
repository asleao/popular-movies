object Build {
    private const val androidBuildToolsPluginVersion = "7.4.2"
    const val androidBuildToolsPlugin = "com.android.tools.build:gradle:$androidBuildToolsPluginVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val navigationSafeArgsGradlePluginVersion = "2.3.5"
    const val navigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationSafeArgsGradlePluginVersion"

    private const val jUnit5GradlePluginVersion = "1.9.3.0"
    const val jUnit5GradlePlugin =
        "de.mannodermaus.gradle.plugins:android-junit5:$jUnit5GradlePluginVersion"
}
