package com.prof18.kmp.fatframework.cocoa.task.xcframework

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.task.xcframework.common.buildXCFramework
import org.gradle.api.Project
import org.gradle.api.tasks.Exec

internal const val BUILD_DEBUG_XC_FRAMEWORK_TASK_NAME = "buildIosDebugXCFramework"

internal fun Project.registerBuildDebugXCFrameworkTask() {
    tasks.register(BUILD_DEBUG_XC_FRAMEWORK_TASK_NAME, Exec::class.java) {
        description = "Create a Debug XCFramework"

        val config = getConfigurationOrThrow()
        buildXCFramework(config, config.debugFatFrameworkList)
    }
}