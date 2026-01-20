import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.compose") version "1.10.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
}

group = "idiotamspielen"
version = "0.2.3-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

// setting up a mockito agent for testing
val mockitoAgent: Configuration by configurations.creating

dependencies {
    // 1. Compose Desktop Dependencies
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material:material:1.10.0")
    implementation("org.jetbrains.compose.ui:ui:1.10.0")
    implementation("org.jetbrains.compose.foundation:foundation:1.10.0")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")

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

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
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