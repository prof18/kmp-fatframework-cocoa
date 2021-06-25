package com.prof18.kmp.fatframework.cocoa.task

import com.google.common.truth.Truth.assertThat
import com.prof18.kmp.fatframework.cocoa.data.PluginConfigErrorMessages
import com.prof18.kmp.fatframework.cocoa.task.fatframework.BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME
import com.prof18.kmp.fatframework.cocoa.testutils.TestUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

class FatFrameworkTasksWithoutFieldsTest {

    private lateinit var testProject: File
    private lateinit var buildGradleFile: File
    private lateinit var gradleFileStringBuilder: StringBuilder

    @Before
    fun setup() {
        val testProjectName = "test-project"
        testProject = File("src/test/resources/$testProjectName")
        buildGradleFile = File("src/test/resources/$testProjectName/build.gradle.kts")

        gradleFileStringBuilder = StringBuilder()
        gradleFileStringBuilder.append(TestUtils.baseGradleFile)
    }

    @After
    fun cleanUp() {
        buildGradleFile.deleteRecursively()
        File("${testProject.path}/build").deleteRecursively()
        File("${testProject.path}/.gradle").deleteRecursively()
    }

    @Test
    fun `task returns error when fat framework name is not present`() {
        val pluginConfig = """
           fatFrameworkCocoaConfig {
           }
       """.trimIndent()

        gradleFileStringBuilder.append("\n")
        gradleFileStringBuilder.append(pluginConfig)
        buildGradleFile.writeText(gradleFileStringBuilder.toString())

        val runner = GradleRunner.create()
            .withProjectDir(testProject)
            .withPluginClasspath()

        val result = runner
            .withArguments(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME, "--stacktrace")
            .buildAndFail()

        assertThat(result.output)
            .contains(PluginConfigErrorMessages.FRAMEWORK_NAME_NOT_PRESENT_MESSAGE)

    }

    @Test
    fun `task return error when output path is not present`() {

        val pluginConfig = """
           fatFrameworkCocoaConfig {
                frameworkName = "LibraryName"
           }
       """.trimIndent()

        gradleFileStringBuilder.append("\n")
        gradleFileStringBuilder.append(pluginConfig)
        buildGradleFile.writeText(gradleFileStringBuilder.toString())

        val runner = GradleRunner.create()
            .withProjectDir(testProject)
            .withPluginClasspath()

        val result = runner
            .withArguments(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME, "--stacktrace")
            .buildAndFail()

        assertThat(result.output)
            .contains(PluginConfigErrorMessages.OUTPUT_PATH_NOT_PRESENT_MESSAGE)

    }

    @Test
    fun `task return error when version name is not present`() {

        val pluginConfig = """
           fatFrameworkCocoaConfig {
                frameworkName = "LibraryName"
                outputPath = "${testProject.path}/../test-dest"
           }
       """.trimIndent()

        gradleFileStringBuilder.append("\n")
        gradleFileStringBuilder.append(pluginConfig)
        buildGradleFile.writeText(gradleFileStringBuilder.toString())

        val runner = GradleRunner.create()
            .withProjectDir(testProject)
            .withPluginClasspath()

        val result = runner
            .withArguments(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME, "--stacktrace")
            .buildAndFail()

        assertThat(result.output)
            .contains(PluginConfigErrorMessages.VERSION_NAME_NOT_PRESENT_MESSAGE)

    }
}