group = "de.l.oklab.gentri"
version = "0.1-SNAPSHOT"

plugins {
    application
    id("com.github.ben-manes.versions")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    implementation("org.mongodb:mongodb-driver-sync:_")
    implementation("dev.morphia.morphia:morphia-core:_")
    implementation("com.fasterxml.jackson.core:jackson-core:_")
    implementation("com.fasterxml.jackson.core:jackson-annotations:_")
    implementation("com.fasterxml.jackson.core:jackson-databind:_")
    implementation(kotlin("stdlib"))
}

application {
    mainClass.value("de.l.oklab.gentri.mongo.ImporterMainKt")
}



