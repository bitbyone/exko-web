rootProject.name = "exko-web"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include("kotlin-html")
include("kotlin-htmx")
include("kotlin-webawesome")
include("spring-htmx")
include("spring-hotswap-agent")
include("playground")
