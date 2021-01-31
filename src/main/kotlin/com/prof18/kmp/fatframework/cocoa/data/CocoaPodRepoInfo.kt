package com.prof18.kmp.fatframework.cocoa.data

import com.prof18.kmp.fatframework.cocoa.KMPFatFrameworkCocoaPlugin

data class CocoaPodRepoInfo(
    var summary: String = "",
    var homepage: String = "",
    var license: String = "",
    var authors: String = "",
    var gitUrl: String = ""
) {
    internal companion object {
        internal val templateFile =
            KMPFatFrameworkCocoaPlugin::class.java.getResource("/template/Framework.podspec.template")
    }
}