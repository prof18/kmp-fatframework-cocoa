package com.prof18.kmp.fatframework.cocoa.task.xcframework

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.task.xcframework.common.buildXCFramework
import org.gradle.api.Project
import org.gradle.api.tasks.Exec

internal const val BUILD_RELEASE_XC_FRAMEWORK_TASK_NAME = "buildIosReleaseXCFramework"

internal fun Project.registerBuildReleaseXCFrameworkTask() {
    tasks.register(BUILD_RELEASE_XC_FRAMEWORK_TASK_NAME, Exec::class.java) {
        description = "Create a Release XCFramework"

        val config = getConfigurationOrThrow()
        buildXCFramework(config, config.releaseFatFrameworkList)
    }
}