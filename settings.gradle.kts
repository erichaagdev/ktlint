pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise") version("3.11.4")
    id("com.gradle.common-custom-user-data-gradle-plugin") version("1.8.2")
}

val isCI = !System.getenv("CI").isNullOrEmpty() // adjust to your CI provider

gradleEnterprise {
    server = "https://ge.solutions-team.gradle.com/" // adjust to your GE server
    allowUntrustedServer = false // ensure a trusted certificate is configured

    buildScan {
        capture { isTaskInputFiles = true }
        isUploadInBackground = !isCI
        publishAlways()
    }
}

buildCache {
    local {
        isEnabled = true
    }

    // Use the Gradle Enterprise connector's access key based authentication.
    // This is available in Gradle Enterprise 2022.3+ and Gradle Enterprise Plugin 3.11+.
    remote(gradleEnterprise.buildCache) {
        isEnabled = true
        isPush = isCI
    }
}

rootProject.name = "ktlint-root"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":ktlint",
    ":ktlint-core",
    ":ktlint-reporter-baseline",
    ":ktlint-reporter-checkstyle",
    ":ktlint-reporter-format",
    ":ktlint-reporter-json",
    ":ktlint-reporter-sarif",
    ":ktlint-reporter-html",
    ":ktlint-reporter-plain",
    ":ktlint-ruleset-experimental",
    ":ktlint-ruleset-standard",
    ":ktlint-ruleset-template",
    ":ktlint-ruleset-test",
    ":ktlint-test",
    ":ktlint-test-logging",
)
