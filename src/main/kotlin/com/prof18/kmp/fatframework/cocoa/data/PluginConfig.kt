package com.prof18.kmp.fatframework.cocoa.data

import com.prof18.kmp.fatframework.cocoa.FAT_FRAMEWORK_COCOA_EXTENSION
import com.prof18.kmp.fatframework.cocoa.KMPFatFrameworkCocoaExtension
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

@Throws(InvalidUserDataException::class)
fun Project.getConfigurationOrThrow() = PluginConfig.of(
    extensions.findByName(FAT_FRAMEWORK_COCOA_EXTENSION) as KMPFatFrameworkCocoaExtension
)

class PluginConfig private constructor(
    val debugFatFrameworkList: List<Framework>,
    val releaseFatFrameworkList: List<Framework>,
    val fatFrameworkName: String,
    val outputPath: String,
    val namePrefix: String?
) {
    internal companion object {
        fun of(extension: KMPFatFrameworkCocoaExtension): PluginConfig {
            return PluginConfig(
                // TODO: user get or null and throw error,
                //  and improve with error messages.
                debugFatFrameworkList = extension.debugFrameworkList,
                releaseFatFrameworkList = extension.releaseFrameworkList,
                fatFrameworkName = extension.fatFrameworkName.get(),
                outputPath = extension.outputPath.get(),
                namePrefix = extension.namePrefix.orNull
            )
        }
    }
}