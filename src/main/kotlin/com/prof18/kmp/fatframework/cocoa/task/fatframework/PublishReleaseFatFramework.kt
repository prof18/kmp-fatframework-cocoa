package com.prof18.kmp.fatframework.cocoa.task.fatframework

import com.prof18.kmp.fatframework.cocoa.data.getConfigurationOrThrow
import com.prof18.kmp.fatframework.cocoa.task.common.PREPARE_COCOA_REPO_FOR_RELEASE_TASK_NAME
import com.prof18.kmp.fatframework.cocoa.task.common.publishReleaseFramework
import org.gradle.api.Project

internal const val PUBLISH_RELEASE_FAT_FRAMEWORK_TASK_NAME = "publishReleaseIosFatFramework"

internal fun Project.registerPublishReleaseFatFrameworkTask() {
    tasks.register(PUBLISH_RELEASE_FAT_FRAMEWORK_TASK_NAME) {
        description = "Publish the release FatFramework to a CocoaPod repository"

        val config = getConfigurationOrThrow()

        mustRunAfter(PREPARE_COCOA_REPO_FOR_RELEASE_TASK_NAME)
        dependsOn(PREPARE_COCOA_REPO_FOR_RELEASE_TASK_NAME)
        dependsOn(BUILD_RELEASE_FAT_FRAMEWORK_TASK_NAME)

        doLast {
            publishReleaseFramework(config)
        }
    }
}