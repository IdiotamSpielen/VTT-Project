plugins {
    kotlin("jvm") version "2.1.0" // Kotlin Plugin
    id("org.openjfx.javafxplugin") version "0.0.8" // JavaFX Plugin
}

group = "idiotamspielen"
version = "0.2.3-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.openjfx:javafx-controls:24-ea+19")
    implementation("org.openjfx:javafx-fxml:24-ea+19")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.0")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.1")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.12")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    version = "24-ea+19"
    modules("javafx.controls", "javafx.fxml")
}

tasks.withType<Test> {
    useJUnitPlatform()
}