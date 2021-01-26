package com.prof18.kmp.fatframework.cocoa.task

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import java.io.File

const val BUILD_DEBUG_AND_RELEASE_FAT_FRAMEWORK_TASK_NAME = "buildDebugAndReleaseFatFramework"

fun Project.registerBuildFatFramework() =
    tasks.register(BUILD_DEBUG_AND_RELEASE_FAT_FRAMEWORK_TASK_NAME, FatFrameworkTask::class.java) {
        description = "Create a Fat Framework with both the debug and release targets"

        val config = getConfigurationOrThrow()
        for (framework in config.debugFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        for (framework in config.releaseFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        baseName = config.fatFrameworkName
        from(config.debugFatFrameworkList + config.releaseFatFrameworkList)
        destinationDir = File(config.outputPath)
    }