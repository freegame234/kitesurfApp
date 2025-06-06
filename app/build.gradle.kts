plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.kitesurf"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kitesurf"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Jetpack Compose & Material 3
    implementation(platform(libs.androidx.compose.bom)) // Import Compose BOM for version management
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)

    // ExoPlayer & Media3
    implementation(libs.androidx.media3.exoplayer.v131) // ExoPlayer
    implementation(libs.androidx.media3.ui.v131) // UI for ExoPlayer
    implementation(libs.androidx.media3.common) // Often useful for handling media
    implementation(libs.androidx.media3.datasource.okhttp) // HTTP support for external URLs

    // Hilt (Dependency Injection)
    implementation(libs.androidx.hilt.navigation.compose.v100)
    ksp(libs.hilt.android.compiler.v2562) // Or "com.google.dagger:hilt-android-compiler:HILT_VERSION"
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Retrofit (Networking)
    implementation(libs.retrofit)
    implementation(libs.converter.gson.v2110)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Room (Local Database)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Lifecycle and LiveData
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    kspAndroidTest(libs.hilt.android.compiler.v2461)

    // Google Maps and Location
    implementation(libs.maps.compose.v2140)
    implementation(libs.play.services.maps.v1810)
    implementation(libs.play.services.location.v2101)

    // Coil for image loading
    implementation(libs.coil.compose)

    // UI and Foundation
    implementation(libs.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.navigation.compose.v253)

    // Logging Interceptor
    implementation(libs.logging.interceptor) // Logging for HTTP requests

    implementation(libs.accompanist.swiperefresh)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.datastore.preferences)


}
