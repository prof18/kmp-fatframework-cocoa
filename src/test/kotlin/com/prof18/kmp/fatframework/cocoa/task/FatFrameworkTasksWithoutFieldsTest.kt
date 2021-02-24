package com.prof18.kmp.fatframework.cocoa.task

import com.google.common.truth.Truth.assertThat
import com.prof18.kmp.fatframework.cocoa.data.PluginConfigErrorMessages
import com.prof18.kmp.fatframework.cocoa.testutils.TestUtils
import org.gradle.testkit.runner.GradleRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileWriter

class FatFrameworkTasksWithoutFieldsTest {

    // TODO: clean with before and after
    private lateinit var testProject: File
    private lateinit var buildGradleFile: File
    private lateinit var gradleFileStringBuilder: StringBuilder

    @Before
    fun setup() {
        val testProjectName = "test-project"
        testProject = File("src/test/resources/$testProjectName")
        buildGradleFile =  File("src/test/resources/$testProjectName/build.gradle.kts")

        gradleFileStringBuilder = StringBuilder()
        gradleFileStringBuilder.append(TestUtils.baseGradleFile)
    }

    @After
    fun cleanUp() {
        buildGradleFile.deleteRecursively()
    }

    @Test
    fun `task returns error when fat framework name is not present`() {
//        val pluginConfig = """
//           fatFrameworkCocoaConfig {
//           }
//       """.trimIndent()

//        gradleFileStringBuilder.append("\n")
//        gradleFileStringBuilder.append(pluginConfig)
        buildGradleFile.writeText(gradleFileStringBuilder.toString())

        val runner = GradleRunner.create()
            .withProjectDir(testProject)
            .withPluginClasspath()

        val result = runner
            .withArguments("buildIosDebugFatFramework", "--stacktrace")
            .buildAndFail()

        assertThat(result.output)
            .contains(PluginConfigErrorMessages.FAT_FRAMEWORK_NAME_NOT_PRESENT_MESSAGE)

    }

    // TODO: test without output path

    // TODO: test without namePrefix

    // TODO: test without version name



}