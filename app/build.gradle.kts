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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // Specify the Compose compiler version
    }
}

dependencies {

    implementation(libs.androidx.media3.exoplayer.v131) // Utilisez la dernière version stable
    implementation(libs.androidx.media3.ui.v131) // Composants UI pour ExoPlayer
    implementation(libs.androidx.media3.common) // Souvent utile

    // Les dépendances pour HTTP sont importantes pour les URLs externes comme YouTube
    implementation(libs.androidx.media3.datasource.okhttp)

    // Core Android & Kotlin
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose) // For using Compose with Activity

    // Jetpack Compose UI & Material 3
    implementation(platform(libs.androidx.compose.bom)) // Import Compose BOM for version management
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    // debugImplementation(libs.androidx.ui.test.manifest) // Often needed for UI tests debug

    // Hilt (Dependency Injection)
    implementation(libs.androidx.hilt.navigation.compose.v100)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler.v2562) // Or "com.google.dagger:hilt-android-compiler:HILT_VERSION"
    implementation(libs.androidx.navigation.compose)
    ksp(libs.androidx.room.compiler.v261) // Correct version

    // Retrofit (Networking)
    implementation(libs.retrofit) // Or "com.squareup.retrofit2:retrofit:RETROFIT_VERSION"
    //implementation(libs.converter.gson) // Or "com.squareup.retrofit2:converter-gson:GSON_CONVERTER_VERSION"
    implementation(libs.converter.gson.v2110) // Correct version

    // Coroutines (Asynchronous programming)
    implementation(libs.kotlinx.coroutines.core) // Or "org.jetbrains.kotlinx:kotlinx-coroutines-core:COROUTINES_VERSION"
    implementation(libs.kotlinx.coroutines.android) // Or "org.jetbrains.kotlinx:kotlinx-coroutines-android:COROUTINES_VERSION"

    // Room (Local Database)
    implementation(libs.androidx.room.runtime) // Or "androidx.room:room-runtime:ROOM_VERSION"
    ksp(libs.androidx.room.compiler) // Or "androidx.room:room-compiler:ROOM_VERSION"
    implementation(libs.androidx.room.ktx)

    // LiveData (if needed, often used withViewModel)
    implementation(libs.androidx.lifecycle.livedata.ktx) // Or "androidx.lifecycle:lifecycle-livedata-ktx:LIFECYCLE_VERSION"
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Testing
    testImplementation(libs.junit) // Or "junit:junit:JUNIT_VERSION"
    androidTestImplementation(libs.androidx.junit) // Or "androidx.test.ext:junit:ANDROIDX_JUNIT_VERSION"
    androidTestImplementation(libs.androidx.espresso.core) // Or "androidx.test.espresso:espresso-core:ESPRESSO_VERSION"
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Use BOM for test dependencies too
    androidTestImplementation(libs.androidx.ui.test.junit4) // Compose UI test rules

    // Hilt testing (for instrumented tests)
    androidTestImplementation(libs.hilt.android.testing) // Or "com.google.dagger:hilt-android-testing:HILT_VERSION"
    kspAndroidTest(libs.dagger.hilt.android.compiler) // Or "com.google.dagger:hilt-android-compiler:HILT_VERSION"

    // Optional: Mocking framework for unit tests
    testImplementation(libs.mockk) // Or Mockito

    // Lecture video
    implementation(libs.androidx.media3.exoplayer) // Utilisez la dernière version stable
    implementation(libs.androidx.media3.ui) // Composants UI pour ExoPlayer

    implementation(libs.logging.interceptor) // Replace with the latest version if needed
    // Google Maps Compose
    implementation(libs.maps.compose.v2140)

    // Google Play Services Maps
    implementation(libs.play.services.maps.v1810)

}