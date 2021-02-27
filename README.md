# KMP FatFramework Cocoa

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.prof18.kmp.fatframework.cocoa/com.prof18.kmp.fatframework.cocoa.gradle.plugin/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.prof18.kmp.fatframework.cocoa/com.prof18.kmp.fatframework.cocoa.gradle.plugin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

**KMP FatFramework Cocoa** is a Gradle plugin for Kotlin Multiplatform projects that generates a FatFramework for iOs
targets and manages the publishing process in a CocoaPod Repository.

## Installation

The library is uploaded on MavenCentral, so you can easily add the dependency on the `plugins` block:

```kotlin
plugins {
    id("com.prof18.kmp.fatframework.cocoa") version "<latest-version>"
}
```

## Usage

The plugin adds five Gradle tasks to your project.

- `buildIosDebugFatFramework` that creates a FatFramework with the `Debug` target.


- `buildIosReleaseFatFramework` that creates a FatFramework with the `Release` target.


- `generateCocoaPodRepoForIosFatFramework` that generates a CocoaPod repository ready to host the FatFramework.


- `publishIosDebugFatFramework` that publishes the `Debug` version of the FatFramework in the CocoaPod repository.
  The task takes care of everything:
  - changing the working branch from main/master to develop;
  - building the debug framework;
  - updating the version name inside the Podspec file;
  - committing the changes;
  - and publishing to remote.

  In this way, in the iOs project, you can you the latest changes published on the develop branch:

  ```ruby
  pod '<your-library-name>', :git => "git@github.com:<git-username>/<repo-name>.git", :branch => 'develop'
  ```
  To run this task, the output path provided in the [configuration](#configuration) must be a git repository.
  <br>


- `publishIosReleaseFatFramework` that publishes the `Release` version of the FatFramework in the CocoaPod repository.
  The task takes care of everything:
  - changing the working branch from develop to main/master;
  - building the release framework;
  - updating the version name inside the Podspec file;
  - committing the changes;
  - tagging the commit;
  - and publishing to remote.

  In this way, in the iOs project, you have to specify a version:

  ```ruby
  pod '<your-library-name>', :git => "git@github.com:<git-username>/<repo-name>.git", :tag => '<version-number>'
  ```

  To run this task, the output path provided in the [configuration](#configuration) must be a git repository.

## Configuration

You can configure the plugin with the `fatFrameworkCocoaConfig` block in your `build.gradle.[kts]`.

The mandatory fields are three:

- the name of the FatFramework
- the output path
- the version name

```kotlin
fatFrameworkCocoaConfig {
    fatFrameworkName = "LibraryName"
    outputPath = "$rootDir/../test-dest"
    versionName = "1.0"
}
```

You can also provide a `namePrefix` for the Framework.

```kotlin
namePrefix = "NamePrefix"
```

If you want to run the `generateCocoaPodRepoForIosFatFramework` task to generate a CocoaPod repository, you have to
provide the mandatory fields mentioned above and some other parameters in the `cocoaPodRepoInfo` block:

- a summary of the library
- the homepage of the library
- the license of the library
- the authors of the library
- the url of the git repository that hosts the CocoaPod repo.

```kotlin
fatFrameworkCocoaConfig {
    fatFrameworkName = "LibraryName"
    outputPath = "$rootDir/../test-dest"
    versionName = "1.0"

    cocoaPodRepoInfo {
        summary = "This is a test KMP framework"
        homepage = "https://github.com/prof18/ccoca-repo-test"
        license = "Apache"
        authors = "\"Marco Gomiero\" => \"mg@mail.it\""
        gitUrl = "git@github.com:prof18/ccoca-repo-test.git"
    }
}
```

Just as reference, the following block shows all the possible fields:

```kotlin
fatFrameworkCocoaConfig {
    fatFrameworkName = "LibraryName"
    outputPath = "$rootDir/../test-dest"
    namePrefix = "LibraryName"
    versionName = "1.0"

    cocoaPodRepoInfo {
        summary = "This is a test KMP framework"
        homepage = "https://github.com/prof18/ccoca-repo-test"
        license = "Apache"
        authors = "\"Marco Gomiero\" => \"mg@mail.it\""
        gitUrl = "git@github.com:prof18/ccoca-repo-test.git"
    }
}
```

## Sample Project

To see the plugin in action, I've published [a little sample project](https://github.com/prof18/kmp-fatframework-test-project).

## Further Readings

This plugin is born from a set of unbundled Gradle tasks that I was copying between every Kotlin Multiplatform project.
I've written about these
tasks [in an article on my website](https://www.marcogomiero.com/posts/2021/kmp-existing-project/).

For more info about CocoaPod repo, I suggest reading the following resources:

- https://guides.cocoapods.org/making/private-cocoapods.html
- https://guides.cocoapods.org/

## License

```
   Copyright 2021 Marco Gomiero

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
