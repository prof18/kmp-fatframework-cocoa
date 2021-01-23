package com.prof18.kmp.fatframework.cocoa.task

import com.prof18.kmp.fatframework.cocoa.getConfigurationOrThrow
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.internal.impldep.com.esotericsoftware.minlog.Log
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.js.dce.InputResource.Companion.file
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.io.File
import org.gradle.api.Project

//abstract class TemplateExampleTask : FatFrameworkTask() {
//
//    init {
//        description = "Just a sample template task"
//
//        // Don't forget to set the group here.
////         group = BasePlugin.BUILD_GROUP
//    }
//
//    @get:Input
//    @get:Option(option = "message", description = "A message to be printed in the output file")
//    abstract val message: Property<String>
//
//    @get:Input
//    @get:Option(option = "tag", description = "A Tag to be used for debug and in the output file")
//    @get:Optional
//    abstract val tag: Property<String>
//
//    @get:Input
//    @get:Option(option = "tag", description = "A Tag to be used for debug and in the output file")
//    abstract val frameworkList: ListProperty<Framework>
//
//    @get:OutputFile
//    abstract val outputFile: RegularFileProperty
//
//    @TaskAction
//    fun sampleAction() {
//
////        logger.log(LogLevel.INFO, "Here!")
////
////        println("AAAAAAAAAAAA")
////
////        val prettyTag = tag.orNull?.let { "[$it]" } ?: ""
////
////        logger.lifecycle("$prettyTag message is: ${message.orNull}")
////        logger.lifecycle("$prettyTag tag is: ${tag.orNull}")
////        logger.lifecycle("$prettyTag outputFile is: ${outputFile.orNull}")
////
////        outputFile.get().asFile.writeText("$prettyTag ${message.get()}")
//        val frameworks: MutableList<Framework> = frameworkList.get()
//        baseName = "library"
//        from(
//            frameworks
//        )
//        destinationDir = File("/Users/marco/Workspace/Personal/KMP/FatFrameworkGradelPlugin/test-dest")
//        group = "library"
//        description = "Create the debug framework for iOs"
//        dependsOn("linklibraryDebugFrameworkIosArm64")
//        dependsOn("linklibraryDebugFrameworkIosX64")
//    }
//
////    internal fun getFramework(buildConfiguration: BuildConfiguration): AppleFramework? =
////        try {
////            val nativeBinary = nativeTarget.binaries.find { binary ->
////                binary.buildType.getName().equals(buildConfiguration.name, ignoreCase = true) &&
////                        binary.outputKind == NativeOutputKind.FRAMEWORK
////            }
////            AppleFramework.of(nativeBinary)
////        } catch (_: Exception) { null }
//}


fun Project.registerBuildDebugFatFramework() = tasks.register("buildDebugFatFramework", FatFrameworkTask::class.java) {



    val config = getConfigurationOrThrow()

    println(">>>>> ARONNE")

    baseName = "library"
    from(
        config.frameworkList
    )
    destinationDir = File("/Users/marco/Workspace/Personal/KMP/FatFrameworkGradelPlugin/test-dest")
    group = "library"
    description = "Create the release framework for iOs"

    dependsOn("linkLibraryIosArm64")
    dependsOn("linkLibraryIosX64")


}