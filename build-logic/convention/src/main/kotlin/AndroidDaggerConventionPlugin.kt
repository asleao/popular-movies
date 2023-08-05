import br.com.popularmovies.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDaggerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                add("implementation", (libs.findLibrary("dagger").get()))
                add("kapt", (libs.findLibrary("dagger.compiler").get()))
                add("kaptAndroidTest", (libs.findLibrary("dagger.compiler").get()))
            }
        }
    }

}