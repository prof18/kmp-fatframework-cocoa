package com.prof18.kmp.fatframework.cocoa

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.OutputDirectory
import org.gradle.kotlin.dsl.listProperty
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import java.io.File
import javax.inject.Inject

const val DEFAULT_OUTPUT_FILE = "template-example.txt"

@Suppress("UnnecessaryAbstractClass")
abstract class TemplateExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    val message: Property<String> = objects.property(String::class.java)

    val tag: Property<String> = objects.property(String::class.java)

//    var frameworkList: PropertyColl<MutableList<Framework>> = objects.property(List<Framework>::class.java)

    internal val frameworkList: MutableList<Framework> = mutableListOf()



    val outputFile: RegularFileProperty = objects.fileProperty().convention(
        project.layout.buildDirectory.file(DEFAULT_OUTPUT_FILE)
    )
}
