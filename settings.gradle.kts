rootProject.name = "VTT-Project"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            content {
                includeGroup("org.jetbrains.compose")
                includeGroup("org.jetbrains.compose.hot-reload")
                includeGroup("org.jetbrains.kotlin.plugin.compose")
            }
        }
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            content {
                includeGroup("org.jetbrains.compose")
                includeGroup("org.jetbrains.compose.hot-reload")
                includeGroup("org.jetbrains.kotlin.plugin.compose")
            }
        }
    }
}
