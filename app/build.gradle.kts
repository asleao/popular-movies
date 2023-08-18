@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.application")
    id("popularmovies.android.application.compose")
    id("popularmovies.android.dagger")
    id("androidx.navigation.safeargs.kotlin")
    id("scabbard.gradle")
}

android {
    namespace = Namespaces.app
    defaultConfig {
        applicationId = ProjectConfig.applicationId
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            applicationIdSuffix = ProjectBuildType.RELEASE.applicationIdSuffix
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ProjectBuildType.DEBUG.applicationIdSuffix
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        dataBinding = true //TODO check if it really needed
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    scabbard {
        fullBindingGraphValidation = true
        enabled = true
        qualifiedNames = false
        outputFormat = "svg"
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:home_api"))
    implementation(project(":feature:movie_details"))
    implementation(project(":feature:movie_details_api"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:domain_api"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:data_api"))
    implementation(project(":core:data"))
    implementation(project(":core:datasource_remote"))
    implementation(project(":core:datasource_remote_api"))
    implementation(project(":core:datasource_db"))
    implementation(project(":core:datasource_db_api"))

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.ui)
}