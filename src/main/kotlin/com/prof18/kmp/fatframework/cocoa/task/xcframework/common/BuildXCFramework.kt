package com.prof18.kmp.fatframework.cocoa.task.xcframework.common

import com.prof18.kmp.fatframework.cocoa.data.PluginConfig
import com.prof18.kmp.fatframework.cocoa.utils.dsymFile
import org.gradle.api.tasks.Exec
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import java.io.File

internal fun Exec.buildXCFramework(config: PluginConfig, frameworks: List<Framework>) {
    for (framework in frameworks) {
        dependsOn(framework.linkTaskName)
    }

    val xcFrameworkDest = File("${config.outputPath}/${config.frameworkName}.xcframework")

    // Params taken from https://github.com/ge-org/multiplatform-swiftpackage/blob/master/src/main/kotlin/com/chromaticnoise/multiplatformswiftpackage/task/CreateXCFrameworkTask.kt
    executable = "xcodebuild"
    args(mutableListOf<String>().apply {
        add("-create-xcframework")
        add("-output")
        add(xcFrameworkDest.path)
        for (framework in frameworks) {
            add("-framework")
            add(framework.outputFile.path)

            framework.dsymFile().takeIf { it.exists() }?.let { dsymFile ->
                add("-debug-symbols")
                add(dsymFile.path)
            }
        }
    })

    doFirst {
        xcFrameworkDest.deleteRecursively()
    }
}