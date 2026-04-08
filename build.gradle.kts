import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.compose") version "1.10.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
}

group = "com.idiotamspielen" // Optional: Dein Group ID
version = System.getenv("GITHUB_REF_NAME")?.removePrefix("v") ?: "1.0.0"

kotlin {
    jvmToolchain(21)
}

// setting up a mockito agent for testing
val mockitoAgent: Configuration by configurations.creating

dependencies {
    // --- Compose Desktop ---
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material:material:1.10.0")
    implementation("org.jetbrains.compose.ui:ui:1.10.0")
    implementation("org.jetbrains.compose.foundation:foundation:1.10.0")
    implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")

    // --- Database (SQLite + Exposed) ---
    implementation("org.jetbrains.exposed:exposed-core:1.0.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.0.0")
    implementation("org.jetbrains.exposed:exposed-dao:1.0.0")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")

    // --- Logging ---
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.+")

    // --- Testing ---
    //coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.2")

    //Mockito
    testImplementation("org.mockito:mockito-core:5.+")
    testImplementation("org.mockito:mockito-junit-jupiter:5.+")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    mockitoAgent("org.mockito:mockito-core:5.+") { isTransitive = false }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

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
            targetFormats(TargetFormat.Exe, TargetFormat.Deb)
            packageName = "OpenVTT"
            packageVersion = "0.5.0"

            description = "A local-first Virtual Tabletop"
            copyright = "© 2026 IdiotamSpielen"

            includeAllModules = true

            windows {
                menu = true
                shortcut = true
                //iconFile.set(project.file("src/main/resources/icon.ico"))
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from(project.file("proguard-rules.pro"))
        }
    }
}