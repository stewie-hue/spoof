# VirtualCam Lab — Fixed Build Starter

This Android starter app selects an image and displays it inside its own local preview, plus basic device diagnostics.

## Build workflow fix
The workflow intentionally removes `android-actions/setup-android@v3` because earlier project logs showed it trying to resolve the obsolete SDK package `tools`. It uses the GitHub runner's preinstalled Android SDK at `/usr/local/lib/android/sdk`, checks for Android platform 35 and Build Tools 35.0.0, and installs those packages only if missing.

## Build from a phone
1. Upload the contents of this ZIP to a GitHub repository, preserving paths.
2. Open Actions and run **Build VirtualCam Lab APK**.
3. Download the `virtualcam-lab-debug-apk` artifact from a successful run.
4. Extract it and install `app-debug.apk`.

## Boundaries
The app only previews the chosen image inside itself. It does not replace the system camera, inject images into other apps, or bypass KYC/liveness checks.
