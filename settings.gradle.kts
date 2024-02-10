pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mental Health with GPT"
include(":app")
include(":core")
include(":core:common")
include(":core:common-impl")
include(":core:presentation")
include(":core:themes")
