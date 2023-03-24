import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    kotlin("jvm") version "1.8.10"
    id("org.jmailen.kotlinter") version "3.14.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.kraktun.kbotexample"
version = "0.0.8"

val kBotVersion = "3de8d58"
val exposedVersion = "0.41.1"
val sqliteVersion = "3.41.2.0"
val telegramVersion = "6.5.0"
val kUtilsVersion = "e710803"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambots-meta:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")
    implementation("com.github.kraktun:kbot:$kBotVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("com.github.kraktun:kutils:$kUtilsVersion")
}

application {
    mainClass.set("com.kraktun.kbotexample.MainKt")
}

tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes(mapOf("Implementation-Version" to project.version))
        }
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.logging.log4j") {
            useVersion("2.20.0")
            because("fix vulnerability")
        }
    }
}
