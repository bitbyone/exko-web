plugins {
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(project(":kotlin-webawesome"))
    implementation(project(":spring-htmx"))
    implementation(project(":spring-hotswap-agent"))
    implementation(libs.springBootStarterWeb)
    implementation(libs.jacksonKotlinModule)
    developmentOnly(libs.springBootDevtools)
}
