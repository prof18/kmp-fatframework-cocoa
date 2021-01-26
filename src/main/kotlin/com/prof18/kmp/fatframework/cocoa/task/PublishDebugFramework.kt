package com.prof18.kmp.fatframework.cocoa.task

import org.gradle.api.Project


const val PUBLISH_DEBUG_FRAMEWORK_TASK_NAME = "publishDebugFramework"

fun Project.registerPublishDebugFramework() =
    tasks.register(PUBLISH_DEBUG_FRAMEWORK_TASK_NAME) {
        description = "Publish the debug framework to a cocoa pod repository"

        dependsOn(BUILD_DEBUG_FAT_FRAMEWORK_TASK_NAME)


    }


/*

fun Project.registerBuildFatFramework() =
    tasks.register(BUILD_DEBUG_AND_RELEASE_FAT_FRAMEWORK_NAME, FatFrameworkTask::class.java) {
        description = "Create a Fat Framework with both the debug and release targets"

        val config = getConfigurationOrThrow()
        for (framework in config.debugFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        for (framework in config.releaseFatFrameworkList) {
            dependsOn(framework.linkTaskName)
        }
        baseName = config.fatFrameworkName
        from(config.debugFatFrameworkList + config.releaseFatFrameworkList)
        destinationDir = File(config.outputPath)
    }

    register("publishDevFramework") {
            description = "Publish iOs framweork to the Cocoa Repo"

            project.exec {
                workingDir = File("$rootDir/../../hn-foundation-cocoa")
                commandLine("git", "checkout", "develop").standardOutput
            }

            // Create Release Framework for Xcode
            dependsOn("universalFrameworkDebug")

            // Replace
            doLast {
                val dir = File("$rootDir/../../hn-foundation-cocoa/HNFoundation.podspec")
                val tempFile = File("$rootDir/../../hn-foundation-cocoa/HNFoundation.podspec.new")

                val reader = dir.bufferedReader()
                val writer = tempFile.bufferedWriter()
                var currentLine: String?

                while (reader.readLine().also { currLine -> currentLine = currLine } != null) {
                    if (currentLine?.startsWith("s.version") == true) {
                        writer.write("s.version       = \"${libVersionName}\"" + System.lineSeparator())
                    } else {
                        writer.write(currentLine + System.lineSeparator())
                    }
                }
                writer.close()
                reader.close()
                val successful = tempFile.renameTo(dir)

                if (successful) {

                    val dateFormatter = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine(
                            "git",
                            "commit",
                            "-a",
                            "-m",
                            "\"New dev release: ${libVersionName}-${dateFormatter.format(Date())}\""
                        ).standardOutput
                    }

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "push", "origin", "develop").standardOutput
                    }
                }
            }
        }

 */