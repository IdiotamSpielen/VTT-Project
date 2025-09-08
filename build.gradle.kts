import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.2.0" // Kotlin Plugin
    id("org.openjfx.javafxplugin") version "0.1.0" // JavaFX Plugin
    id("com.gradleup.shadow") version "9.1.0" //Shadow Plugin
    id("edu.sc.seis.launch4j") version "3.0.0"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.0")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("edu.sc.seis.launch4j:launch4j:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.4")
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
        kotlin.srcDirs("src/main/kotlin")
    }
    test {
        java.srcDirs("src/test/java") // Testquellen für Java
        kotlin.srcDirs("src/test/kotlin") // Testquellen für Kotlin
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    modularity.inferModulePath.set(true)
}

javafx {
    version = "24-ea+19"
    modules("javafx.controls", "javafx.fxml")
}

launch4j {
    mainClassName = "idiotamspielen.vttproject.MainKt"
    jarTask = tasks.shadowJar.get()
    outfile = "build/VTT.exe"
}

application {
    mainClass.set("idiotamspielen.vttproject.MainKt") // Hauptklasse
}

tasks {
    shadowJar {
        archiveBaseName.set("VTT")
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
        mergeServiceFiles()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}