rootProject.name = "exko-web"

include("kotlin-html")
include("kotlin-htmx")
include("kotlin-webawesome")
include("spring-htmx")
include("spring-hotswap-agent")
include("ui-kit-sandbox")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}
