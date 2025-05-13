pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Declare plugins used in sub-projects, without applying them here
        //noinspection GradlePluginVersion
        id("com.android.application") version "8.10.0" apply false
        //noinspection GradlePluginVersion
        id("com.android.library") version "8.10.0" apply false // If you have library modules
        id("org.jetbrains.kotlin.android") version "2.1.20" apply false
        id("org.jetbrains.kotlin.jvm") version "2.1.20" apply false // If you have JVM modules
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false // Kotlin Compose plugin
        id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false // Kotlin Symbol Processing
        id("com.google.dagger.hilt.android") version "2.56.2" apply false // Hilt Gradle Plugin
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kitesurf"
include(":app")
