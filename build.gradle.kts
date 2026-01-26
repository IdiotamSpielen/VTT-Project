import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.0"
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
    // Compose Desktop Dependencies
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material:material:1.10.0")
    implementation("org.jetbrains.compose.ui:ui:1.10.0")
    implementation("org.jetbrains.compose.foundation:foundation:1.10.0")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")

    // libraries for working with database
    implementation("org.jetbrains.exposed:exposed-core:1.0.0-rc-3")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0-rc-3")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0-rc-3")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")

    // logic libraries (might become obsolete in this change)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.0")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.21")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.2")
    testImplementation("org.mockito:mockito-core:5.+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.2")
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