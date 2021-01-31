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

fun Project.executeBashCommand(showOutput: Boolean = true, workingDirFile: File, commandList: List<String>): String {
    return ByteArrayOutputStream().use { outputStream ->
        project.exec {
            workingDir = workingDirFile
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

fun Project.execBashCommandThrowExecException(output: File, commandList: List<String>, exceptionMessage: String) {
    try {
        executeBashCommand(
            workingDirFile = output,
            commandList = commandList
        )
    } catch (e: ExecException) {
        throw  ExecException(exceptionMessage)
    }
}

fun Project.retrieveMainBranchName(): String {
    val config = getConfigurationOrThrow()
    var branchName = ""
    try {
        val checkMainOutput = executeBashCommand(
            showOutput = false,
            workingDirPath = config.outputPath,
            commandList = listOf("git", "branch", "--list", "main")
        )
        if (checkMainOutput.contains("main")) {
            branchName = "main"
        }

        println(branchName)

        if (branchName.isEmpty()) {
            val checkMasterOutput = executeBashCommand(
                showOutput = false,
                workingDirPath = config.outputPath,
                commandList = listOf("git", "branch", "--list", "master")
            )
            if (checkMasterOutput.contains("master")) {
                branchName = "master"
            }
        }
    } catch (e: ExecException) {
        throw ExecException("Error while checking if the main or master branch exists. Are you sure it does exists?")
    }
    return branchName
}