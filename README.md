# Aryan-YT--Engine 🚀

A high-level, developer-friendly Android library for efficient YouTube stream metadata extraction. Get HLS, DASH, and progressive stream links in a clean JSON format.

## Features ✨
- **Simple API**: Initialize and extract data with just a few lines of code.
- **Clean JSON Output**: Structured response optimized for mobile apps.
- **Adaptive Bitrate Ready**: Supports HLS/DASH manifest URLs for live streams.
- **High Performance**: Optimized for fast extraction on Android.

---

## Installation 📦

### 1. Add JitPack repository
Depending on your project type, choose one:

#### For Kotlin DSL (`settings.gradle.kts`):
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // ✅ Use equals and uri()
    }
}
```

#### For Groovy (`settings.gradle`):
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Add dependency
In your app's **`build.gradle.kts`** (or `build.gradle`):

```gradle
dependencies {
    // Current latest commit: f6e4d8d
    implementation 'com.github.my-skills-app:Aryan-YT--Engine:f6e4d8d'
}
```

### 3. Add Permissions 🛡️
**IMPORTANT**: You MUST add the following permission to your **`AndroidManifest.xml`** or the library will fail with a "Permission Denied" error:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## Simple Usage 🛠️

### 1. Initialization
Make sure to initialize the engine once (e.g., in your Activity `onCreate`):

```java
import com.aryan.yt.engine.AryanYT;

// Initialize
AryanYT.init();
```

### 2. Metadata Extraction
Run extraction in a background thread:

```java
new Thread(() -> {
    try {
        String videoUrl = "https://www.youtube.com/live/Nq2wYlWFucg";
        String jsonResult = AryanYT.extractJson(videoUrl);
        
        // Use JSON in your app
        Log.d("AryanYT", jsonResult);
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}).start();
```

---

## JSON Response Format 📄
The library returns data as follows:

```json
{
  "videoId": "Nq2wYlWFucg",
  "title": "...",
  "status": "OK",
  "isLive": true,
  "streams": {
    "combined": [...],   
    "videoOnly": [...],  
    "audioOnly": [...],  
    "live": {
       "hlsUrl": "...",  // Use this for Live Streams (.m3u8)
       "dashUrl": "..."  // Use this for Live Streams (.mpd)
    }
  }
}
```

---

## Troubleshooting 💡
- **Error: Permission Denied**: Check if you added the `<uses-permission android:name="android.permission.INTERNET" />` to your manifest.
- **Build Failure (KTS)**: Ensure you are using `maven { url = uri("https://jitpack.io") }` in your `settings.gradle.kts`.

---

## License 📜
GPL-3.0 License

Developed by [Aryan](https://github.com/my-skills-app)
