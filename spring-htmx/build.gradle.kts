plugins {
    kotlin("plugin.allopen")
}

dependencies {
    api(project(":kotlin-htmx"))
    implementation(libs.springBootStarterWeb)
}
