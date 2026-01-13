import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.compose") version "1.10.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    application
}

group = "idiotamspielen"
version = "0.2.3-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
}

// Konfiguration für den Mockito-Agent (Tests von final classes)
val mockitoAgent: Configuration by configurations.creating

dependencies {
    // 1. Compose Desktop Dependencies
    implementation(compose.desktop.currentOs)
    implementation(compose.material) // Für Button, TextField, Scaffold etc.
    implementation(compose.ui)
    implementation(compose.foundation)

    // 2. Deine Logik-Bibliotheken (bleiben erhalten)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.21")

    // 3. Testing (identisch zu vorher)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testImplementation("org.mockito:mockito-core:5.+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

    mockitoAgent("org.mockito:mockito-core:5.21.0") { isTransitive = false }
}

application {
    // Hinweis: Die Main-Klasse ist jetzt die Datei, in der deine fun main() steht.
    mainClass.set("MainKt")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
        // Wichtig für Compose:
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Mockito Agent einbinden
    val agentFile = mockitoAgent.find { it.name.startsWith("mockito-core") }
    if (agentFile != null) {
        jvmArgs("-javaagent:${agentFile.absolutePath}")
    }
    jvmArgs("-XX:+EnableDynamicAgentLoading")

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Exe)
            packageName = "PersonalVTT"
            packageVersion = "0.2.3"
        }
    }
}