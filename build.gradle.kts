import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    kotlin("jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.kraktun.kbotexample"
version = "0.0.5"

val kBotVersion = "6db8cb9"
val exposedVersion = "0.35.1"
val sqliteVersion = "3.36.0.2"
val telegramVersion = "5.3.0"
val kUtilsVersion = "441931a"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambots-meta:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")
    implementation("com.github.Kraktun:KBot:$kBotVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("com.github.kraktun:kutils:$kUtilsVersion")
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    disabledRules.set(setOf("no-wildcard-imports"))
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
