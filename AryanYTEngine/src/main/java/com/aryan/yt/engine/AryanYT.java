package com.aryan.yt.engine;

import android.util.Log;

import com.aryan.yt.engine.downloader.Downloader;
import com.aryan.yt.engine.downloader.Request;
import com.aryan.yt.engine.downloader.Response;
import com.aryan.yt.engine.stream.AudioStream;
import com.aryan.yt.engine.stream.StreamInfo;
import com.aryan.yt.engine.stream.VideoStream;
import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * High-level API for AryanYTEngine to provide clean JSON data for developers.
 */
public class AryanYT {
    private static final String TAG = "AryanYT";

    /**
     * Initializes the engine with a default URLConnection downloader.
     */
    public static void init() {
        NewPipe.init(new Downloader() {
            @Override
            public Response execute(Request request) throws java.io.IOException {
                URL url = new URL(request.url());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(request.httpMethod());
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
                    conn.setRequestProperty(entry.getKey(), String.join(", ", entry.getValue()));
                }

                if (request.dataToSend() != null && (request.httpMethod().equalsIgnoreCase("POST") || request.httpMethod().equalsIgnoreCase("PUT"))) {
                    conn.setDoOutput(true);
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(request.dataToSend());
                    }
                }

                int responseCode = conn.getResponseCode();
                InputStream is = (responseCode >= 200 && responseCode < 400) ? conn.getInputStream() : conn.getErrorStream();

                String responseBody = "";
                if (is != null) {
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }
                    responseBody = result.toString("UTF-8");
                }

                return new Response(responseCode, conn.getResponseMessage(), conn.getHeaderFields(), responseBody, request.url());
            }
        });
    }

    /**
     * Fetches video information and returns it as a formatted JSON string.
     */
    public static String extractJson(String url) throws Exception {
        StreamInfo info = StreamInfo.getInfo(ServiceList.YouTube, url);
        return convertToJson(info);
    }

    private static String convertToJson(StreamInfo info) {
        JsonObject root = new JsonObject();
        
        // Basic Info
        root.put("videoId", info.getId());
        root.put("title", info.getName());
        root.put("uploader", info.getUploaderName());
        root.put("status", "OK"); // Defaulting to OK if extraction succeeded
        root.put("isLive", info.getStreamType().toString().contains("LIVE"));
        
        JsonObject streamsObj = new JsonObject();
        
        // 1. Combined Streams (Video+Audio)
        JsonArray combined = new JsonArray();
        for (VideoStream s : info.getVideoStreams()) {
            combined.add(streamToJson(s));
        }
        streamsObj.put("combined", combined);

        // 2. Video Only Streams
        JsonArray videoOnly = new JsonArray();
        for (VideoStream s : info.getVideoOnlyStreams()) {
            videoOnly.add(streamToJson(s));
        }
        streamsObj.put("videoOnly", videoOnly);

        // 3. Audio Only Streams
        JsonArray audioOnly = new JsonArray();
        for (AudioStream s : info.getAudioStreams()) {
            audioOnly.add(audioStreamToJson(s));
        }
        streamsObj.put("audioOnly", audioOnly);

        // 4. Live Manifests
        JsonObject live = new JsonObject();
        live.put("hlsUrl", info.getHlsUrl().isEmpty() ? null : info.getHlsUrl());
        live.put("dashUrl", info.getDashMpdUrl().isEmpty() ? null : info.getDashMpdUrl());
        streamsObj.put("live", live);

        root.put("streams", streamsObj);

        return JsonWriter.string(root);
    }

    private static JsonObject streamToJson(VideoStream s) {
        JsonObject obj = new JsonObject();
        obj.put("itag", s.getItag());
        obj.put("mimeType", s.getFormat() != null ? s.getFormat().getName() : "unknown");
        obj.put("quality", s.getResolution());
        obj.put("resolution", s.getResolution());
        obj.put("bitrate", 0); // Bitrate not always available directly in VideoStream without more logic
        obj.put("url", s.getUrl());
        return obj;
    }

    private static JsonObject audioStreamToJson(AudioStream s) {
        JsonObject obj = new JsonObject();
        obj.put("itag", s.getItag());
        obj.put("mimeType", s.getFormat() != null ? s.getFormat().getName() : "unknown");
        obj.put("bitrate", s.getAverageBitrate());
        obj.put("url", s.getUrl());
        return obj;
    }
}
