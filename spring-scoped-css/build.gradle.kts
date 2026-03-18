plugins {
    kotlin("plugin.allopen")
}

dependencies {
    api(project(":kotlin-scoped-css"))
    api(project(":spring-htmx"))
    implementation(libs.springBootStarterWeb)
    implementation(libs.kotlinLogging)
    compileOnly(libs.hotswapAgentCore)
}
