package com.prof18.kmp.fatframework.cocoa.task.xcframework

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.task.common.PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME
import com.prof18.kmp.fatframework.cocoa.task.common.publishDebugFramework
import org.gradle.api.Project

internal const val PUBLISH_DEBUG_XC_FRAMEWORK_TASK_NAME = "publishDebugXCFramework"

internal fun Project.registerPublishDebugXCFrameworkTask() {

    tasks.register(PUBLISH_DEBUG_XC_FRAMEWORK_TASK_NAME) {
        description = "Publish the debug XCFramework to a CocoaPod repository"

        val config = getConfigurationOrThrow()

        mustRunAfter(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(PREPARE_COCOA_REPO_FOR_DEBUG_TASK_NAME)
        dependsOn(BUILD_DEBUG_XC_FRAMEWORK_TASK_NAME)

        doLast {
            publishDebugFramework(config)
        }
    }
}