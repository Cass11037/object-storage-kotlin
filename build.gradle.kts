plugins {
    kotlin("jvm") version "1.9.0"
    application
    kotlin("plugin.serialization") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.mockk:mockk:1.13.5")
    implementation("org.apache.commons:commons-csv:1.10.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("org.example.MainKt")
}

tasks {
    shadowJar {
        archiveBaseName.set("your-app")
        archiveVersion.set("1.0")
        archiveClassifier.set("")
        manifest {
            attributes["Main-Class"] = application.mainClass.get()
        }
    }
}