plugins {
    id("com.simonharrer.graphviz") version "0.0.1"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    mavenCentral()
}

dependencies {
    implementation("org.graphstream:gs-core:1.3")
    implementation("org.graphstream:gs-algo:1.3")
    implementation("org.graphstream:gs-ui:1.3")
    implementation("org.testng:testng:7.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.mockito:mockito-core:4.8.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

apply(plugin = "com.simonharrer.graphviz")

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}