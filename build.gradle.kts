//plugins {
//    kotlin("jvm") version "1.4.21"
//}

version = "0.0.1-SNAPSHOT"

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.2.4")
    }
}

apply(plugin = "binary-compatibility-validator")

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

//group = "com.prof18"
//artifactId = "kmp.fatframework.cocoa"




repositories {
    jcenter()
}

dependencies {
    compileOnly(gradleApi())

    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.4.21")

//    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
//    testImplementation("io.kotest:kotest-assertions-core:4.3.0")
//    testImplementation("io.kotest:kotest-property:4.3.0")
//    testImplementation("io.mockk:mockk:1.10.0")
//    testImplementation(kotlin("gradle-plugin"))



    //
//    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
//    testImplementation("io.kotest:kotest-assertions-core:4.3.0")
//    testImplementation("io.kotest:kotest-property:4.3.0")
//    testImplementation("io.mockk:mockk:1.10.0")
//    testImplementation(kotlin("gradle-plugin"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
}

gradlePlugin {
    plugins {
        create("com.prof18.kmp.fatframework.cocoa") {
            id = "com.prof18.kmp.fatframework.cocoa"
            implementationClass = "com.prof18.kmp.fatframework.cocoa.TemplatePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            pom {
                groupId = "com.prof18.kmp.fatframework.cocoa"
                artifactId = "com.prof18.kmp.fatframework.cocoa.gradle.plugin"
                version = "0.0.1-SNAPSHOT"


            }
        }
    }

    repositories {
        maven {
            val releasesUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
            name = "mavencentral"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl)
            credentials {
                username = System.getenv("SONATYPE_NEXUS_USERNAME")
                password = System.getenv("SONATYPE_NEXUS_PASSWORD")
            }
        }
    }
}

