# Aryan-YT--Engine 🚀

A high-level, developer-friendly Android library for efficient YouTube stream metadata extraction. This library provides direct access to HLS, DASH, and progressive stream links in a clean JSON format.

## Features ✨
- **Simple API**: Initialize and extract data with just a few lines of code.
- **Clean JSON Output**: Structured response optimized for mobile developers.
- **Adaptive Bitrate Ready**: Prioritizes HLS/DASH manifest URLs for live streams.
- **High Performance**: Built on top of the robust NewPipe Extractor engine.

---

## Installation 📦

### 1. Add JitPack repository
In your **`settings.gradle`** file:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Add dependency
In your app's **`build.gradle`** file:

```gradle
dependencies {
    implementation 'com.github.my-skills-app:Aryan-YT--Engine:595c3af'
}
```

---

## Simple Usage 🛠️

### Header Initialization
```java
// Initialize the engine once (e.g., in your Application or Activity onCreate)
AryanYT.init();
```

### Metadata Extraction
```java
new Thread(() -> {
    try {
        String videoUrl = "https://www.youtube.com/live/Nq2wYlWFucg";
        String jsonResult = AryanYT.extractJson(videoUrl);
        
        // Use the JSON result in your app
        Log.d("AryanYT", jsonResult);
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}).start();
```

---

## JSON Response Format 📄
The library returns a structured JSON object:

```json
{
  "videoId": "xrnANY4uk-A",
  "title": "Video Title",
  "status": "OK",
  "isLive": false,
  "streams": {
    "combined": [...],   // Video + Audio tracks
    "videoOnly": [...],  // Progressive video tracks
    "audioOnly": [...],  // Progressive audio tracks
    "live": {
       "hlsUrl": "...",  // Playlist URL (.m3u8)
       "dashUrl": "..."  // Manifest URL (.mpd)
    }
  }
}
```

---

## License 📜
This project is licensed under the GPL-3.0 License - see the LICENSE file for details.

Developed with ❤️ by [Aryan](https://github.com/my-skills-app)
