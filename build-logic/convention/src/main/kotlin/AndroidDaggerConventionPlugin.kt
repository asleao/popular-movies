import br.com.popularmovies.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDaggerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                add("implementation", (libs.findLibrary("dagger").get()))
                add("ksp", (libs.findLibrary("dagger.compiler").get()))
                add("kspAndroidTest", (libs.findLibrary("dagger.compiler").get()))
            }
        }
    }

}