package com.prof18.kmp.fatframework.cocoa.task

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import java.io.File

const val BUILD_RELEASE_FAT_FRAMEWORK_TASK_NAME = "buildReleaseFatFramework"

fun Project.registerBuildReleaseFatFramework() =
    tasks.register(BUILD_RELEASE_FAT_FRAMEWORK_TASK_NAME, FatFrameworkTask::class.java) {
        description = "Create a Release Fat Framework"

        val config = getConfigurationOrThrow()
        for (framework in config.releaseFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        baseName = config.fatFrameworkName
        from(config.releaseFatFrameworkList)
        destinationDir = File(config.outputPath)
    }