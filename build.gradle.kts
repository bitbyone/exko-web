import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinSpring) apply false
    alias(libs.plugins.springBoot) apply false
    alias(libs.plugins.springDependencyManagement)
    `maven-publish`
}

group = "io.exko"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jvm.get())
    }
}

allprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }
}

subprojects {
    val plugins = rootProject.libs.plugins
    val libs = rootProject.libs

    apply(plugin = plugins.kotlin.get().pluginId)
    apply(plugin = plugins.springDependencyManagement.get().pluginId)
    if (project.name != "playground") {
        apply(plugin = "maven-publish")
        apply(plugin = "java-library")
    } else {
        apply(plugin = plugins.kotlinSpring.get().pluginId)
    }

    // to override spring dep management for the current spring version
    ext["kotlin.version"] = libs.versions.kotlin.get()

    dependencyManagement {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        implementation(libs.kotlinStdlib)
        implementation(libs.kotlinLogging)
        testImplementation(libs.junitJupiter)
        testImplementation(libs.junitPlatformLauncher)
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xcontext-parameters",
                "-Xjsr305=strict",
                "-Xemit-jvm-type-annotations",
            )
            apiVersion.set(KotlinVersion.KOTLIN_2_2)
            languageVersion.set(KotlinVersion.KOTLIN_2_2)
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.get()))
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    if (project.name != "playground") {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    groupId = project.group.toString()
                    artifactId = project.name
                    version = project.version.toString()
                    from(components["java"])
                }
            }
        }
    }

    tasks.register("dependenciesSpring") {
        group = "help"
        doLast {
            dependencyManagement.importedProperties.forEach(::println)
        }
    }
}
