# VirtualCam Lab

Safe Android camera-testing starter project. It lets you choose an image and preview it inside this app, with basic device diagnostics.

## Included
- Image picker and local preview
- Basic diagnostics screen
- GitHub Actions APK build workflow
- ARM64-compatible Android app configuration

## Boundaries
This project does not replace Android's system camera feed, inject images into other apps, or bypass identity/KYC/liveness checks.

## Build using GitHub from your phone
1. Create a GitHub repository named `VirtualCam-Lab`.
2. Upload these files and preserve their folder structure.
3. Open **Actions** and enable workflows if prompted.
4. Run **Build VirtualCam Lab APK**.
5. Download the `virtualcam-lab-debug-apk` artifact from the completed workflow.
6. Extract the artifact ZIP and install `app-debug.apk`.

Build settings: JDK 17, Android SDK 35, Gradle 8.9.
