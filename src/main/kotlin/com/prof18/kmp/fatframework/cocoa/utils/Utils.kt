package com.prof18.kmp.fatframework.cocoa.utils

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import org.gradle.api.Project
import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream
import java.io.File

fun Project.executeBashCommand(showOutput: Boolean = true, workingDirPath: String, commandList: List<String>): String {
    return ByteArrayOutputStream().use { outputStream ->
        project.exec {
            workingDir = File(workingDirPath)
            commandLine(commandList)
            standardOutput = outputStream
        }
        val output = outputStream.toString()
        if (showOutput) {
            print(output)
        }
        return@use output
    }
}

fun Project.execBashCommandInRepoAndThrowExecException(commandList: List<String>, exceptionMessage: String) {
    val config = getConfigurationOrThrow()
    try {
        executeBashCommand(
            workingDirPath = config.outputPath, // TODO: change to cocoa repo path
            commandList = commandList
        )
    } catch (e: ExecException) {
        throw  ExecException(exceptionMessage)
    }
}