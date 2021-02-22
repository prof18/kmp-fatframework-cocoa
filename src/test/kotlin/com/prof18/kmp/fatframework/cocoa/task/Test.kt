package com.prof18.kmp.fatframework.cocoa.task

import com.google.common.truth.Truth.assertThat
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test
import java.io.File
import java.io.FileWriter

class KMPFatFrameworkCocoaPluginTest {

    // TODO: clean with before and after

    @Test
    fun `test plugin`() {
        val fixtureName = "test-project"
        val fixtureRoot = File("src/test/resources/$fixtureName")
        val gradle = File("src/test/resources/$fixtureName/build.gradle.kts")

        val toWriteBuilder = StringBuilder()
        toWriteBuilder.append(baseBuildGradle)

        val toWrite = """
           fatFrameworkCocoaConfig {
               namePrefix = "LibraryName"
           }
       """.trimIndent()

        toWriteBuilder.append("\n")
        toWriteBuilder.append(toWrite)

        gradle.writeText(toWriteBuilder.toString())

        val runner = GradleRunner.create()
            .withProjectDir(fixtureRoot)
            .withPluginClasspath()

        val result = runner
            .withArguments("buildIosDebugFatFramework", "--stacktrace")
            .buildAndFail()

        assertThat(result.output)
            .contains("test")

        gradle.deleteRecursively()
    }

    val baseBuildGradle = """
        plugins {
            kotlin("multiplatform") version "1.4.30"
            id("com.prof18.kmp.fatframework.cocoa")
        }

        repositories {
            mavenCentral()
            jcenter()
        }

        kotlin {
            ios() {
                binaries.framework("LibraryName")
            }
            sourceSets {
                val commonMain by getting
                val iosMain by getting
            }
        }
    """.trimIndent()

}