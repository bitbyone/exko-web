plugins {
    kotlin("plugin.allopen")
}

dependencies {
    api(project(":styled"))
    api(project(":spring-htmx"))
    implementation(libs.springBootStarterWeb)
    implementation(libs.kotlinLogging)
    implementation(libs.hotswapAgentCore)
}
