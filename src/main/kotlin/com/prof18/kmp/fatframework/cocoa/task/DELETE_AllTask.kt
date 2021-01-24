package com.prof18.kmp.fatframework.cocoa.task

/*

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

        register("publishFramework") {
            description = "Publish iOs framework to the Cocoa Repo"

            project.exec {
                workingDir = File("$rootDir/../../hn-foundation-cocoa")
                commandLine("git", "checkout", "master").standardOutput
            }

            // Create Release Framework for Xcode
            dependsOn("universalFrameworkRelease")

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

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "commit", "-a", "-m", "\"New release: ${libVersionName}\"").standardOutput
                    }

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "tag", libVersionName).standardOutput
                    }

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "push", "origin", "master", "--tags").standardOutput
                    }
                }
            }
        }

        register("publishAll") {
            description = "Publish JVM and Android artifacts to Nexus and push iOs framweork to the Cocoa Repo"
            // Publish JVM and Android artifacts to Nexus
            dependsOn("publish")
            // Create Release Framework for Xcode
            dependsOn("universalFrameworkRelease")

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
                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "commit", "-a", "-m", "\"New release: ${libVersionName}\"").standardOutput
                    }

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "tag", libVersionName).standardOutput
                    }

                    project.exec {
                        workingDir = File("$rootDir/../../hn-foundation-cocoa")
                        commandLine("git", "push", "origin", "master", "--tags").standardOutput
                    }
                }
            }
        }

 */