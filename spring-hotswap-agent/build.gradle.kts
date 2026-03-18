plugins {
    kotlin("plugin.allopen")
}

dependencies {
    implementation(libs.kotlinCoroutinesCore)
    compileOnly(libs.springBootDevtools)
    implementation(project(":spring-htmx"))
}
