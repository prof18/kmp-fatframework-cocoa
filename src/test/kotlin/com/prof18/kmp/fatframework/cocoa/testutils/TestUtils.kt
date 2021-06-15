package com.prof18.kmp.fatframework.cocoa.testutils

object TestUtils {

    val baseGradleFile = """
        plugins {
            kotlin("multiplatform") version "1.5.10"
            id("com.prof18.kmp.fatframework.cocoa")
        }

        repositories {
            mavenCentral()
            jcenter()
        }

        kotlin {
            ios() {
                binaries.framework()
            }
            sourceSets {
                val commonMain by getting
                val iosMain by getting
            }
        }
    """.trimIndent()

}


/*


fatFrameworkCocoaConfig {
    fatFrameworkName = "LibraryName"
    outputPath = "$rootDir/../test-dest"
    namePrefix = "LibraryName"
    versionName = "1.2-SNAPSHOT"

    cocoaPodRepoInfo {
        summary = "This is a test KMP framework"
        homepage = "https://github.com/prof18/ccoca-repo-test"
        license = "Apache"
        authors = "\"Revelop Team\" => \"team@uniwhere.com\""
        gitUrl = "git@github.com:prof18/ccoca-repo-test.git"
    }

}

 */