# VirtualCam Lab — JVM target fix

The build log showed Java compiling for JVM 1.8 while Kotlin compiled for JVM 17. This version explicitly sets Java source/target compatibility and Kotlin `jvmTarget` to 17 in `app/build.gradle.kts`.

The GitHub Actions workflow also avoids `android-actions/setup-android@v3`, which previously attempted to install the obsolete SDK package `tools`, and checks the runner's preinstalled SDK.

This app is a local image-preview/testing starter only. It does not replace system camera output or inject images into third-party apps.

Build from a phone: upload this project to GitHub, run **Build VirtualCam Lab APK** under Actions, then download the `virtualcam-lab-debug-apk` artifact.
