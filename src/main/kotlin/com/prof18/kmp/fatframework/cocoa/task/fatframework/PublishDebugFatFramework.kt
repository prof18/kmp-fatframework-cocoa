package com.prof18.kmp.fatframework.cocoa.task.fatframework

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.task.common.PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME
import com.prof18.kmp.fatframework.cocoa.task.common.publishDebugFramework
import org.gradle.api.Project

internal const val PUBLISH_DEBUG_FAT_FRAMEWORK_TASK_NAME = "publishIosDebugFatFramework"

internal fun Project.registerPublishDebugFatFrameworkTask() {

    tasks.register(PUBLISH_DEBUG_FAT_FRAMEWORK_TASK_NAME) {
        description = "Publish the debug FatFramework to a CocoaPod repository"

        val config = getConfigurationOrThrow()

        mustRunAfter(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME)

        doLast {
            publishDebugFramework(config)
        }
    }
}