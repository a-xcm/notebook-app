pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradle(Plugin)
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Qorder"
include(":app")
