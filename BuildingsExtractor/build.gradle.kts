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
    implementation("org.openstreetmap.osmosis:osmosis-core:0.47")
    implementation("org.openstreetmap.osmosis:osmosis-extract:0.47")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:0.47")
    implementation("org.openstreetmap.osmosis:osmosis-core:0.47")
    implementation("org.openstreetmap.osmosis:osmosis-extract:0.47")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:0.47")
    api("junit:junit:4.12")
    implementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
    implementation(kotlin("stdlib"))
}

application {
    mainClassName = "de.l.oklab.gentri.buildings.ParserMain"
}



