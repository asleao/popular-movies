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
include(":feature:home")
include(":feature:home_api")
include(":feature:movie_details")
include(":feature:movie_details_api")
include(":core:common")
include(":core:data")
include(":core:data_api")
include(":core:datasource_db")
include(":core:datasource_db_api")
include(":core:datasource_remote")
include(":core:datasource_remote_api")
include(":core:domain")
include(":core:domain_api")
include(":core:designsystem")
include(":core:model")
include(":core:ui")
include(":core:worker")
include(":core:worker_api")
include(":feature:search_movies")
include(":feature:search_movies_api")
