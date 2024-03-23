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
include(":features")
include(":core:common")
include(":core:common-impl")
include(":core:presentation")
include(":core:themes")
include(":features:sign-in")
include(":data")
include(":features:sign-up")
include(":features:profile")
include(":features:home")
include(":features:assistant")
