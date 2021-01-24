package com.prof18.kmp.fatframework.cocoa

import com.prof18.kmp.fatframework.cocoa.task.registerBuildDebugFatFramework
import com.prof18.kmp.fatframework.cocoa.task.registerBuildReleaseFatFramework
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

abstract class KMPFatFrameworkCocoaPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension =
            project.extensions.create(FAT_FRAMEWORK_COCOA_EXTENSION, KMPFatFrameworkCocoaExtension::class.java, project)

        project.afterEvaluate {
            project.extensions.findByType<KotlinMultiplatformExtension>()
                ?.let { kmpExtension: KotlinMultiplatformExtension ->

                    val nativeTargetList: List<KotlinNativeTarget> = kmpExtension.targets.toList()
                        .filterIsInstance<KotlinNativeTarget>()
                        .filter { it.konanTarget.family.isAppleFamily }

                    val namePrefix = extension.namePrefix.orNull

                    for (nativeTarget in nativeTargetList) {
                        val debugFramework: Framework = if (namePrefix == null) {
                            nativeTarget.binaries.getFramework( "Debug")
                        } else {
                            nativeTarget.binaries.getFramework( namePrefix,"Debug")
                        }
                        val releaseFramework: Framework = if (namePrefix == null) {
                            nativeTarget.binaries.getFramework( "Release")
                        } else {
                            nativeTarget.binaries.getFramework( namePrefix,"Release")
                        }
                        extension.debugFrameworkList.add(debugFramework)
                        extension.releaseFrameworkList.add(releaseFramework)
                    }

                    // Register Tasks
                    project.registerBuildDebugFatFramework()
                    project.registerBuildReleaseFatFramework()
                    project.registerBuildReleaseFatFramework()
                }
        }


    }


}

// TODO: improve all of this with messages and stuff


