plugins {
    id("com.android.library")
    kotlin("multiplatform") version "1.4.0"
    id("kotlin-android-extensions")
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

val libName = "HNFoundation"
val libGroup = "com.prof18.hn.foundation"
val libVersionName = "1.0.0"
val libVersionCode = 10000

group = libGroup
version = libVersionName