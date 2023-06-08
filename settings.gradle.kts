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
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
        google()
        mavenCentral()
    }
}

rootProject.name = "Sample App"
include(":app")
