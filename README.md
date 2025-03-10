# Getting Started with Android Studio

Welcome to **Getting Started with Android Studio**! This project serves as a beginner-friendly guide to setting up and building Android applications using **Jetpack Compose**, and more.

## 📌 Features

- **Jetpack Compose UI** - Modern UI toolkit for building native Android UIs.
- **Gallery Image Selection** - Pick and display images from the gallery.
- **Audio Recording & Playback** - Record and play audio within the app.
- **Gyroscope Sensor** - Detect device rotation and trigger actions.
- **SharedPreferences** - Save and load user data locally.

## 🚀 Getting Started

### 1️⃣ Prerequisites

Ensure you have the following installed:

- **Android Studio** (latest version)
- **Java 11+ or Kotlin**
- **Android SDK** & **AVD Emulator** (or a physical device)
- **Gradle** (bundled with Android Studio)

### 2️⃣ Clone the Repository

```sh
git clone https://github.com/SK-Tareen/Getting-Started-Android-Studio.git
cd Getting-Started-Android-Studio
```

### 3️⃣ Open in Android Studio

1. Launch **Android Studio**.
2. Click **Open** and select the project folder.
3. Wait for Gradle to sync.

### 4️⃣ Run the App

- Connect a **physical Android device** or launch an **Android Emulator**.
- Click the **Run ▶** button in Android Studio.
  

## 🛠 Technologies Used

This project is built using:

- **Kotlin** - Primary language for Android development  
- **Jetpack Compose** - Modern UI toolkit for building native UIs  
- **CameraX** - For integrating camera functionality  
- **MediaPlayer & AudioRecorder** - For audio recording and playback  
- **SharedPreferences** - For storing user data persistently  
- **Coil** - For loading and displaying images  
- **Sensors (Gyroscope)** - For detecting device rotation  
- **Android Jetpack Libraries** - Including Navigation, ViewModel, and LiveData  

## 📜 Permissions Required

This app requires the following permissions for full functionality:

```xml
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
    <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```



