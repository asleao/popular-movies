object Compose {
    const val composeVersion = "1.4.3"
    const val composeCompilerVersion = "1.4.3"
    const val materialVersion = "1.1.1"
    const val material3 = "androidx.compose.material3:material3:$materialVersion"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val materialWindowSize =
        "androidx.compose.material3:material3-window-size-class:${materialVersion}"
    const val materialIconsExtended =
        "androidx.compose.material:material-icons-extended:${materialVersion}"
    const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
    const val compiler = "androidx.compose.compiler:compiler:$composeCompilerVersion"

    private const val navigationVersion = "2.6.0"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val activityComposeVersion = "1.7.2"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.6.1"
    const val lifecycleViewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"

    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
}
