import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("popularmovies.android.application")
    id("popularmovies.android.application.compose")
    id("popularmovies.android.dagger")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = Namespaces.app
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val props = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "keys.properties")))
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "MdbApiKey", props.getProperty("MdbApiKey"))
        }
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String", "MdbApiKey", props.getProperty("MdbApiKey"))
        }
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//
//    kotlinOptions {
//        jvmTarget = Kotlin.jvmTarget
//    }

    buildFeatures {
//        compose = true
        dataBinding = true
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
//    }
//
//    packagingOptions {
//        resources {
//            exclude("META-INF/AL2.0")
//            exclude("META-INF/LGPL2.1")
//            exclude("META-INF/licenses/ASM")
//        }
//    }
//
//    kapt {
//        correctErrorTypes = true
//    }

//    detekt {
//        toolVersion = Detekt.detektVersion
//        config.setFrom(file("$rootDir/detekt.yml"))
//        autoCorrect = true
//        parallel = true
//        basePath = rootDir.absolutePath
//    }


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