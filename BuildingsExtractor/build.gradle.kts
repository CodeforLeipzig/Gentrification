group = "de.l.oklab.gentri"
version = "0.1-SNAPSHOT"

plugins {
    application
    id("com.github.ben-manes.versions")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    implementation("org.openstreetmap.osmosis:osmosis-core:_")
    implementation("org.openstreetmap.osmosis:osmosis-extract:_")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:_")
    implementation("org.openstreetmap.osmosis:osmosis-core:_")
    implementation("org.openstreetmap.osmosis:osmosis-extract:_")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:_")
    implementation(kotlin("stdlib"))
}

application {
    mainClass.value("de.l.oklab.gentri.buildings.ParserMainKt")
}



