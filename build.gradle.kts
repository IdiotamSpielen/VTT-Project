import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.2.21" // Kotlin Plugin
    id("org.openjfx.javafxplugin") version "0.1.0" // JavaFX Plugin
    id("com.gradleup.shadow") version "8.3.9"
    application
}

group = "idiotamspielen"
version = "0.2.3-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    gradlePluginPortal()
}

dependencies {
    implementation("org.openjfx:javafx-controls:24-ea+19")
    implementation("org.openjfx:javafx-fxml:24-ea+19")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.21")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("ch.qos.logback:logback-classic:1.5.21")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testImplementation("org.junit.platform:junit-platform-commons:1.13.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:1.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

    runtimeOnly("org.openjfx:javafx-controls:24-ea+19:win")
    runtimeOnly("org.openjfx:javafx-fxml:24-ea+19:win")
    runtimeOnly("org.openjfx:javafx-graphics:24-ea+19:win")
    runtimeOnly("org.openjfx:javafx-base:24-ea+19:win")
    runtimeOnly("org.slf4j:slf4j-api:2.0.17")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.21")
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin")
    }
    test {
        kotlin.srcDirs("src/test/kotlin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(false)
}

javafx {
    version = "24-ea+19"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("idiotamspielen.vttproject.MainKt") // Hauptklasse
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed") // Zeigt Details zu den Tests
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // Stacktraces anzeigen
        showStandardStreams = true // Standard-Ausgaben anzeigen
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}