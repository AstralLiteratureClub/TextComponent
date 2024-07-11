plugins {
    id("java")
}

group = "bet.astral"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("bet.astral:tuples:1.0.0")
    implementation(project(":"))
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.guava:guava:33.2.1-jre")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}