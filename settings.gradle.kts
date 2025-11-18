rootProject.name = "KMP-App-Template"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
        maven("https://developer.huawei.com/repo/")
        maven("https://developer.hihonor.com/repo")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
        maven("https://developer.huawei.com/repo/")
        maven("https://developer.hihonor.com/repo")
    }
}

include(":composeApp")
// 引入内部组件通信模块 (必要模块)
include(":tuicore")
project(":tuicore").projectDir = File(settingsDir, "../TUIKit/TUICore/tuicore")

include(":timcommon")
project(":timcommon").projectDir = File(settingsDir, "../TUIKit/TIMCommon/timcommon")

include(":tuichat")
project(":tuichat").projectDir = File(settingsDir, "../TUIKit/TUIChat/tuichat")

include(":tuicontact")
project(":tuicontact").projectDir = File(settingsDir, "../TUIKit/TUIContact/tuicontact")

include(":tuiconversation")
project(":tuiconversation").projectDir = File(settingsDir, "../TUIKit/TUIConversation/tuiconversation")

include(":tuicallkit-kt")
project(":tuicallkit-kt").projectDir = File(settingsDir, "../TUIKit/TUICallKit/tuicallkit-kt")
