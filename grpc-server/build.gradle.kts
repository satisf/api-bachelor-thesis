import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.13"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "edu.satisf"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation(project(":grpc-interface"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}