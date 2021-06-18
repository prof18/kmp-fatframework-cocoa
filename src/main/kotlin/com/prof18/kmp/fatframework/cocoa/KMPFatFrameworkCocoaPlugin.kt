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
                        // TODO: filter for apple family when having xcframework
                        .filter { it.konanTarget.family == Family.IOS }

                    val namePrefix = extension.namePrefix

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

//                    for (nativeTarget in nativeTargetList) {
//
////                        nativeTarget.binaries.getFramework(NativeBuildType.DEBUG).outputFile
//                        println("aronneeee")
//
//                        nativeTarget.binaries.filter { it.outputKind == NativeOutputKind.FRAMEWORK }.forEach {
//                            println("Name: ${it.baseName} - BuildType: ${it.buildType} - prefix: ${it.outputFile.path}")
//                        }
//
////                        println(nativeTarget.binaries.first().buildType)
//
//                        val debug: List<Framework> = nativeTarget.binaries
//                            .filter { it.buildType == NativeBuildType.DEBUG && it.outputKind == NativeOutputKind.FRAMEWORK }
//                            .map {
//                                it as Framework
//                            }
//
//                        println("debug: ${debug.size}")
//                        debug.forEach {
//                            println("${it.outputFile}")
//                            println("${it.name}")
//                        }
//
//                        val release = nativeTarget.binaries.filter { it.buildType == NativeBuildType.RELEASE }
//                        println("Release size: ${release.size}")
//
////                        println(nativeTarget.binaries.size)
////                        println(nativeTarget.binaries.toString())
//                        val debugFramework: Framework? = if (namePrefix == null) {
//                            nativeTarget.binaries.findFramework(NativeBuildType.DEBUG)
//                        } else {
//                            nativeTarget.binaries.findFramework(namePrefix, NativeBuildType.DEBUG)
//                        }
//                        println("debugFramework: $debugFramework")
//                        val releaseFramework: Framework = if (namePrefix == null) {
//                            nativeTarget.binaries.getFramework("Release")
//                        } else {
//                            nativeTarget.binaries.getFramework(namePrefix, "Release")
//                        }
//                        extension.debugFrameworkList.add(debugFramework!!)
//                        extension.releaseFrameworkList.add(releaseFramework)
//                    }

                    // Register Tasks
                    // Cocoa Pod Repo
                    project.registerGenerateCocoaPodRepositoryTask()

                    // Build
                    // FatFramework
                    project.registerBuildDebugFatFrameworkTask()
                    project.registerBuildReleaseFatFrameworkTask()
                    // XC Framework
                    project.registerBuildDebugXCFrameworkTask()
                    project.registerBuildReleaseXCFrameworkTask()

                    // Release
                    project.registerPublishPreparationTasks()
                    // Fat Framework
                    project.registerPublishDebugFatFrameworkTask()
                    project.registerPublishReleaseFatFrameworkTask()
                    // XC Framework
                    project.registerPublishDebugXCFrameworkTask()
                    project.registerPublishReleaseXCFrameworkTask()
                }
        }
    }
}



