@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.application")
    id("popularmovies.android.application.compose")
    id("popularmovies.android.dagger")
    id("androidx.navigation.safeargs.kotlin")
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
        dataBinding = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:datasource_remote")) //Check here
    implementation(project(":core:datasource_remote_api")) //Check here

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.ui)


//    //Testing
//    androidTestImplementation(libs.bundles.uitests)
//    testImplementation(libs.bundles.unittests)
//    testRuntimeOnly(libs.jupiter.engine)

//    debugImplementation(dependency.tests.android.core)
//    debugImplementation(dependency.tests.android.fragment)
}