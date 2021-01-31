package com.prof18.kmp.fatframework.cocoa.task

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import java.io.File

const val BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME = "buildDebugFatFramework"

fun Project.registerBuildDebugFatFrameworkTask() {
    tasks.register(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME, FatFrameworkTask::class.java) {
        description = "Create a Debug Fat Framework"

        val config = getConfigurationOrThrow()
        for (framework in config.debugFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        baseName = config.fatFrameworkName
        from(config.debugFatFrameworkList)
        destinationDir = File(config.outputPath)
    }
}