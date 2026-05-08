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
include("styled")
include("kotlin-stimulus")
include("kotlin-webawesome")
include("spring-htmx")
include("spring-styled")
include("spring-stimulus")
include("spring-hotswap-agent")
include("styled-ksp")
include("webawesome-blocks")
include("playground")
