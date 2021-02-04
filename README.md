# kmp-fatframework-cocoa

```bash

./gradlew buildDebugFatFramework

./gradlew buildReleaseFatFramework

./gradlew generateCocoaPodRepo

./gradlew publishDebugFramework

./gradlew publishReleaseFramework

```

```kotlin
id("com.prof18.kmp.fatframework.cocoa") version "0.0.1-SNAPSHOT"
```

```kotlin
fatFrameworkCocoaConfig {
    fatFrameworkName = "LibraryName"
    outputPath = "$rootDir/../test-dest"
    namePrefix = "LibraryName"
    versionName = "1.1-SNAPSHOT"

    cocoaPodRepoInfo {
        summary = "This is a test KMP framework"
        homepage = "https://github.com/prof18/ccoca-repo-test"
        license = "Apache"
        authors = "\"Revelop Team\" => \"team@uniwhere.com\""
        gitUrl = "git@github.com:prof18/ccoca-repo-test.git"
    }
}
```

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
