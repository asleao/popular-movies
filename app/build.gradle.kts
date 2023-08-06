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

    tasks.withType<Test>() {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":entities"))
    implementation(project(":common"))
    implementation(project(":usecases"))

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.ui)
//    implementation(libs.bundles.network)
    implementation(libs.bundles.paging)

//    //Testing
//    androidTestImplementation(libs.bundles.uitests)
//    testImplementation(libs.bundles.unittests)
//    testRuntimeOnly(libs.jupiter.engine)

//    debugImplementation(dependency.tests.android.core)
//    debugImplementation(dependency.tests.android.fragment)
}