group = "de.l.oklab.gentri"
version = "0.1-SNAPSHOT"

plugins {
    application
    kotlin("jvm") version "1.3.41"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    jcenter()
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    compile("org.mongodb:mongodb-driver-sync:3.10.2")
    compile("dev.morphia.morphia:core:1.5.3")
    compile("com.fasterxml.jackson.core:jackson-core:2.9.9")
    compile("com.fasterxml.jackson.core:jackson-annotations:2.9.9")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.9")
    api("junit:junit:4.12")
    implementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
    implementation(kotlin("stdlib"))
}

application {
    mainClassName = "de.l.oklab.gentri.mongo.ImporterMain"
}



