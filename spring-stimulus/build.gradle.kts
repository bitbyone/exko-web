plugins {
    kotlin("plugin.allopen")
}

dependencies {
    api(project(":kotlin-stimulus"))
    implementation(libs.springBootStarterWeb)
}
