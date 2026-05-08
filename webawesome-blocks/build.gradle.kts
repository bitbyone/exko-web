plugins {
    alias(libs.plugins.ksp)
}

dependencies {
    ksp(project(":styled-ksp"))
    api(project(":kotlin-webawesome"))
    api(project(":styled"))
}
