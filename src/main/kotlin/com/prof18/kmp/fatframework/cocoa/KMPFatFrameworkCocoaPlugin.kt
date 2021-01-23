package com.prof18.kmp.fatframework.cocoa

import com.prof18.kmp.fatframework.cocoa.task.registerBuildDebugFatFramework
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

const val TASK_NAME_FAT_FRAMEWORK_DEBUG_BUILD = "buildDebugFatFramework"

const val EXTENSION_NAME = "templateExampleConfig"
//const val TASK_NAME = "templateExample"

abstract class TemplatePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension = project.extensions.create(EXTENSION_NAME, TemplateExtension::class.java, project)

        // Add a task that uses configuration from the extension object

        project.afterEvaluate {
            project.extensions.findByType<KotlinMultiplatformExtension>()
                ?.let { kmpExtension: KotlinMultiplatformExtension ->

                    // kmpExtension.targets.toList()

                    val x: List<KotlinNativeTarget> = kmpExtension.targets.toList()
                        .filterIsInstance<KotlinNativeTarget>()
                        .filter { it.konanTarget.family.isAppleFamily }


                    for (nativeTarget in x) {
                        val framework = nativeTarget.binaries.getFramework("library", "Debug")
                        extension.frameworkList.add(framework)
                        // TODO: pass also the link task name
                    }

//                extension.frameworks = kmpExtension.targets.toList().get(0)


                   project.registerBuildDebugFatFramework()
                }
        }


    }



}

// TODO: improve all of this with messages and stuff

@Throws(InvalidUserDataException::class)
fun Project.getConfigurationOrThrow() = PluginConfig.of(
    extensions.findByName(EXTENSION_NAME) as TemplateExtension
)


class PluginConfig private constructor(
    val frameworkList: List<Framework>
) {
    internal companion object {
        fun of(extension: TemplateExtension): PluginConfig {
            return PluginConfig(
                frameworkList = extension.frameworkList
            )
        }
    }
}
