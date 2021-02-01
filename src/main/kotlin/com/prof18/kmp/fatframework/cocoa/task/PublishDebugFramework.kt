package com.prof18.kmp.fatframework.cocoa.task

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.utils.execBashCommandInRepoAndThrowExecException
import com.prof18.kmp.fatframework.cocoa.utils.executeBashCommand
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.process.internal.ExecException
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val PUBLISH_DEBUG_FRAMEWORK_TASK_NAME = "publishDebugFramework"
const val PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME = "prepareCocoaRepoForDebug"

fun Project.registerPublishDebugFrameworkTask() {

    tasks.register(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME) {
        description = "Prepare the CocoaPod repository for debug."

        val config = getConfigurationOrThrow()
        // Check if is a git repository
        doLast {
            try {
                executeBashCommand(
                    showOutput = false,
                    workingDirPath = config.outputPath,
                    commandList = listOf("git", "rev-parse", "--is-inside-work-tree")
                )
            } catch (e: ExecException) {
                throw InvalidUserDataException("The provided output folder is not a git repository!")
            }

            // Checkout on develop
            execBashCommandInRepoAndThrowExecException(
                commandList = listOf("git", "checkout", "develop"),
                exceptionMessage = "Error while checking out to the develop branch. Are you sure it does exists?"
            )
        }
    }

    tasks.register(PUBLISH_DEBUG_FRAMEWORK_TASK_NAME) {
        description = "Publish the debug framework to a CocoaPod repository"

        val config = getConfigurationOrThrow()
        val libVersionName = config.versionName

        mustRunAfter(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME)

        doLast {
            val podSpecFile = File("${config.outputPath}/${config.fatFrameworkName}.podspec")
            if (!podSpecFile.exists()) {
                throw ExecException("podspec file does not exists!")
            }

            val tempPodSpecFile = File("${config.outputPath}/${config.fatFrameworkName}.podspec.new")

            val reader = podSpecFile.bufferedReader()
            val writer = tempPodSpecFile.bufferedWriter()
            var currentLine: String?

            try {
                while (reader.readLine().also { currLine -> currentLine = currLine } != null) {
                    if (currentLine?.startsWith("s.version") == true) {
                        writer.write("s.version       = \"$libVersionName\"" + System.lineSeparator())
                    } else {
                        writer.write(currentLine + System.lineSeparator())
                    }
                }
            } catch (e: IOException) {
                throw ExecException("Unable to update the version on the podspec file")
            } finally {
                writer.close()
                reader.close()
            }

            val renameSuccessful = tempPodSpecFile.renameTo(podSpecFile)
            if (renameSuccessful) {
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())

                execBashCommandInRepoAndThrowExecException(
                    commandList = listOf("git", "add", "."),
                    exceptionMessage = "Unable to add the files"
                )

                execBashCommandInRepoAndThrowExecException(
                    commandList = listOf(
                        "git",
                        "commit",
                        "-m",
                        "\"New debug release: ${libVersionName}-${dateFormatter.format(Date())}\""
                    ),
                    exceptionMessage = "Unable to commit the changes"
                )

                execBashCommandInRepoAndThrowExecException(
                    commandList = listOf("git", "push", "origin", "develop"),
                    exceptionMessage = "Unable to push the changes to remote"
                )
            }
        }
    }
}