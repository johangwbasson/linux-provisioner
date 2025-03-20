plugins {
    kotlin("jvm") version "2.1.0"
    application
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "com.linuxprovisioner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("net.johanbasson.linux.provision.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.johanbasson.linux.provision.MainKt"
    }

    // Include all dependencies in the JAR
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("linux-provisioner")
            mainClass.set("net.johanbasson.linux.provision.MainKt")
            buildArgs.add("--no-fallback")
            buildArgs.add("-H:+ReportExceptionStackTraces")
        }
    }
}