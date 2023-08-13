dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

include(":app")
include(":core:common")
include(":core:data")
include(":core:datasource_db")
include(":core:datasource_remote")
include(":core:domain")
include(":core:designsystem")
include(":core:model")
include(":core:ui")
include(":feature:home")
include(":core:datasource_remote_api")
include(":core:domain_api")
include(":core:datasource_db_api")
