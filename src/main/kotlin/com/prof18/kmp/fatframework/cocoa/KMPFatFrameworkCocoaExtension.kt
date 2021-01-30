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

const val FAT_FRAMEWORK_COCOA_EXTENSION = "fatFrameworkCocoaConfig"

@Suppress("UnnecessaryAbstractClass")
abstract class KMPFatFrameworkCocoaExtension @Inject constructor(project: Project) {

    var fatFrameworkName: String? = null
    var outputPath: String? = null
    var namePrefix: String? = null
    var versionName: String? = null

    // Internal variables
    internal val debugFrameworkList: MutableList<Framework> = mutableListOf()
    internal val releaseFrameworkList: MutableList<Framework> = mutableListOf()
}
