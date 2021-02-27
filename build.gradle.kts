plugins {
    java
    application
    maven
    kotlin("jvm") version "1.4.31"
}

group = "com.kraktun.kbot"
version = "0.0.1"
val kBotVersion = "303b36f"
val exposedVersion = "0.21.1"
val sqliteVersion = "3.30.1"
val telegramVersion = "5.0.1"
val kUtilsVersion = "bde9d66"

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambots-meta:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")
    implementation("com.github.Kraktun:KBot:$kBotVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("com.github.Kraktun:KUtils:$kUtilsVersion")
}
