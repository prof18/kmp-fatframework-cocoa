package com.prof18.kmp.fatframework.cocoa

import com.prof18.kmp.fatframework.cocoa.task.common.registerPublishPreparationTasks
import com.prof18.kmp.fatframework.cocoa.task.fatframework.registerBuildDebugFatFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.fatframework.registerBuildReleaseFatFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.fatframework.registerPublishDebugFatFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.fatframework.registerPublishReleaseFatFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.registerGenerateCocoaPodRepositoryTask
import com.prof18.kmp.fatframework.cocoa.task.xcframework.registerBuildDebugXCFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.xcframework.registerBuildReleaseXCFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.xcframework.registerPublishDebugXCFrameworkTask
import com.prof18.kmp.fatframework.cocoa.task.xcframework.registerPublishReleaseXCFrameworkTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeOutputKind
import org.jetbrains.kotlin.konan.target.Family

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
                        .filter {
                            if (extension.useXCFramework) {
                                it.konanTarget.family.isAppleFamily
                            } else {
                                it.konanTarget.family == Family.IOS
                            }
                        }

                    val debugFrameworks: List<Framework> = nativeTargetList
                        .flatMap {
                            it.binaries
                                .filter { binary ->
                                    binary.buildType == NativeBuildType.DEBUG && binary.outputKind == NativeOutputKind.FRAMEWORK
                                }
                                .mapNotNull { binary ->
                                    // Nullable cast just to be safe
                                    binary as? Framework
                                }
                        }

                    val releaseFrameworks: List<Framework> = nativeTargetList
                        .flatMap {
                            it.binaries
                                .filter { binary ->
                                    binary.buildType == NativeBuildType.RELEASE && binary.outputKind == NativeOutputKind.FRAMEWORK
                                }
                                .mapNotNull { binary ->
                                    // Nullable cast just to be safe
                                    binary as? Framework
                                }
                        }

                    extension.debugFrameworkList.addAll(debugFrameworks)
                    extension.releaseFrameworkList.addAll(releaseFrameworks)

                    // Register Tasks
                    // Cocoa Pod Repo
                    project.registerGenerateCocoaPodRepositoryTask()

                    project.registerPublishPreparationTasks()
                    if (extension.useXCFramework) {
                        // Build
                        project.registerBuildDebugXCFrameworkTask()
                        project.registerBuildReleaseXCFrameworkTask()

                        // Release
                        project.registerPublishDebugXCFrameworkTask()
                        project.registerPublishReleaseXCFrameworkTask()
                    } else {
                        // Build
                        project.registerBuildDebugFatFrameworkTask()
                        project.registerBuildReleaseFatFrameworkTask()

                        // Release
                        project.registerPublishDebugFatFrameworkTask()
                        project.registerPublishReleaseFatFrameworkTask()
                    }
                }
        }
    }
}



