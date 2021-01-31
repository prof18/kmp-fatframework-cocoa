package com.prof18.kmp.fatframework.cocoa

import com.prof18.kmp.fatframework.cocoa.data.CocoaPodRepoInfo
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import javax.inject.Inject

const val FAT_FRAMEWORK_COCOA_EXTENSION = "fatFrameworkCocoaConfig"

@Suppress("UnnecessaryAbstractClass")
abstract class KMPFatFrameworkCocoaExtension @Inject constructor(project: Project) {

    var fatFrameworkName: String? = null
    var outputPath: String? = null
    var namePrefix: String? = null
    var versionName: String? = null
    var cocoaPodRepoInfo: CocoaPodRepoInfo = CocoaPodRepoInfo()

    // Internal variables
    internal val debugFrameworkList: MutableList<Framework> = mutableListOf()
    internal val releaseFrameworkList: MutableList<Framework> = mutableListOf()

    fun cocoaPodRepoInfo(configure: CocoaPodRepoDSL.() -> Unit) {
        CocoaPodRepoDSL().also { dsl ->
            dsl.configure()
            cocoaPodRepoInfo = CocoaPodRepoInfo(
                summary = dsl.summary,
                homepage = dsl.homepage,
                license = dsl.license,
                authors = dsl.authors,
                gitUrl = dsl.gitUrl
            )

        }
    }

    fun cocoaPodRepoInfo(configure: Closure<CocoaPodRepoDSL>) {
        cocoaPodRepoInfo { ConfigureUtil.configure(configure, this) }
    }
}
