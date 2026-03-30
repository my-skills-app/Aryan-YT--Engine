package com.aryan.yt.engine;

import com.aryan.yt.engine.downloader.Downloader;
import com.aryan.yt.engine.downloader.Request;
import com.aryan.yt.engine.downloader.Response;
import com.aryan.yt.engine.services.youtube.YoutubeService;
import com.aryan.yt.engine.stream.StreamInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ExtractionTest {

    @Before
    public void setUp() {
        NewPipe.init(new Downloader() {
            @Override
            public Response execute(Request request) throws java.io.IOException {
                URL url = new URL(request.url());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(request.httpMethod());
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                
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
                
                if (responseCode >= 400) {
                    System.err.println("ERROR RESPONSE: " + responseBody);
                }

                return new Response(responseCode, conn.getResponseMessage(), conn.getHeaderFields(), responseBody, request.url());
            }
        });
    }

    @Test
    public void testYoutubeExtraction() {
        try {
            String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"; // Rickroll
            YoutubeService service = ServiceList.YouTube;
            
            System.out.println("--- Starting Extraction ---");
            StreamInfo info = StreamInfo.getInfo(service, videoUrl);
            
            System.out.println("✓ VIDEO EXTRACTED SUCCESSFULLY");
            System.out.println("TITLE: " + info.getName());
            System.out.println("UPLOADER: " + info.getUploaderName());
            System.out.println("VIEWS: " + info.getViewCount());
            
            System.out.println("\n--- VIDEO STREAMS ---");
            info.getVideoStreams().forEach(s -> {
                System.out.println("FORMAT: " + s.getFormat().getName() + " | RES: " + s.getResolution() + " | URL: " + s.getUrl());
            });

            System.out.println("\n--- AUDIO STREAMS ---");
            info.getAudioStreams().forEach(s -> {
                System.out.println("FORMAT: " + s.getFormat().getName() + " | BITRATE: " + s.getAverageBitrate() + " | URL: " + s.getUrl());
            });
            System.out.println("---------------------------");
            
            assertNotNull("StreamInfo should not be null", info);
        } catch (Exception e) {
            System.err.println("X EXTRACTION FAILED");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
