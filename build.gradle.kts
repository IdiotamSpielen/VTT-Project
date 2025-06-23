import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.*

plugins {
    kotlin("jvm") version "2.1.21" // Kotlin Plugin
    id("org.openjfx.javafxplugin") version "0.1.0" // JavaFX Plugin
    id("com.gradleup.shadow") version "8.3.6" //Shadow Plugin
    application
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.21")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
    implementation("org.webjars.npm:types__filewriter:0.0.29")
    implementation("org.jetbrains:annotations:26.0.2")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.18")
}

sourceSets {
    main {
        java.srcDirs("src/main/java") // Setzt Java-Quellenverzeichnis
        kotlin.srcDirs("src/main/kotlin") // Setzt Kotlin-Quellenverzeichnis
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

application {
    mainClass.set("idiotamspielen.vttproject.MainKt") // Hauptklasse
}

tasks {
    shadowJar {
        archiveBaseName.set("VTT") // Name der JAR-Datei
        archiveClassifier.set("")          // Entfernt das "-all"
        archiveVersion.set(project.version.toString())        // Nutzt die in der Version definierte Nummer
        mergeServiceFiles()                // Wichtig für das Zusammenführen
        dependencies {
            include(dependency("org.openjfx:javafx-controls"))
            include(dependency("org.openjfx:javafx-fxml"))
        }
    }
}

tasks.register<Exec>("jpackage") {
    group = "distribution"
    description = "Erstelle ein distributables Anwendungspaket"

    dependsOn("shadowJar") // Stelle sicher, dass die JAR-Datei erstellt wird

    commandLine = listOf(
        "jpackage", // Stelle sicher, dass `jpackage` zur Verfügung steht
        "--input", "build/libs", // Ort, der die generierte JAR enthält
        "--dest", "build/jpackage", // Ort, wo das distributable Paket landen soll
        "--name", "VTT", // Der Name der Anwendung
        "--main-jar", "VTT-0.2.3-SNAPSHOT.jar", // Deine Fat-JAR-Datei
        "--main-class", "idiotamspielen.vttproject.MainKt", // Deine Main-Klasse
        "--type", if (System.getProperty("os.name").lowercase(Locale.getDefault()).contains("windows")) "exe" else "app-image",
        "--add-modules", "javafx.controls,javafx.fxml",
        "--java-options",
            "--enable-preview"
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}