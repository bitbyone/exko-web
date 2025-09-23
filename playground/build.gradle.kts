plugins {
    alias(libs.plugins.springBoot)
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(project(":kotlin-webawesome"))
    implementation(project(":spring-htmx"))
    implementation(project(":spring-hotswap-agent"))
    implementation(libs.springBootStarterWeb)
    implementation(libs.jacksonKotlinModule)
    developmentOnly(libs.springBootDevtools)
}
