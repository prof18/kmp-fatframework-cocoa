package com.prof18.kmp.fatframework.cocoa.data

import com.prof18.kmp.fatframework.cocoa.FAT_FRAMEWORK_COCOA_EXTENSION
import com.prof18.kmp.fatframework.cocoa.KMPFatFrameworkCocoaExtension
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import java.io.File

@Throws(InvalidUserDataException::class)
internal fun Project.getConfigurationOrThrow() = PluginConfig.of(
    extensions.findByName(FAT_FRAMEWORK_COCOA_EXTENSION) as KMPFatFrameworkCocoaExtension
)

internal class PluginConfig private constructor(
    val debugFatFrameworkList: List<Framework>,
    val releaseFatFrameworkList: List<Framework>,
    val fatFrameworkName: String,
    val outputPath: String,
    val namePrefix: String?,
    val versionName: String,
    val cocoaPodRepoInfo: CocoaPodRepoInfo
) {

    internal fun getPodSpecFile() = File("${outputPath}/${fatFrameworkName}.podspec")

    internal companion object {
        fun of(extension: KMPFatFrameworkCocoaExtension): PluginConfig {

            val fatFrameworkName: String = if (extension.fatFrameworkName == null) {
                throw InvalidUserDataException(PluginConfigErrorMessages.FAT_FRAMEWORK_NAME_NOT_PRESENT_MESSAGE)
            } else {
                extension.fatFrameworkName!!
            }
            val outputPath = if (extension.outputPath == null) {
                throw InvalidUserDataException(PluginConfigErrorMessages.OUTPUT_PATH_NOT_PRESENT_MESSAGE)
            } else {
                extension.outputPath!!
            }

            val versionName = if (extension.versionName == null) {
                throw InvalidUserDataException(PluginConfigErrorMessages.VERSION_NAME_NOT_PRESENT_MESSAGE)
            } else {
                extension.versionName!!
            }

            return PluginConfig(
                debugFatFrameworkList = extension.debugFrameworkList,
                releaseFatFrameworkList = extension.releaseFrameworkList,
                fatFrameworkName = fatFrameworkName,
                outputPath = outputPath,
                namePrefix = extension.namePrefix,
                versionName = versionName,
                cocoaPodRepoInfo = extension.cocoaPodRepoInfo
            )
        }
    }
}