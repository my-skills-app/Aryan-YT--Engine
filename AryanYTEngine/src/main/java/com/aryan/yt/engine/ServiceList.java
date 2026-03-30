package com.aryan.yt.engine;

import com.aryan.yt.engine.services.youtube.YoutubeService;

import java.util.List;

/**
 * A list of supported services.
 */
@SuppressWarnings({"ConstantName", "InnerAssignment"}) // keep unusual names and inner assignments
public final class ServiceList {
    private ServiceList() {
        // no instance
    }

    public static final YoutubeService YouTube = new YoutubeService(0);

    /**
     * When creating a new service, put this service in the end of this list,
     * and give it the next free id.
     */
    private static final List<StreamingService> SERVICES = List.of(YouTube);

    /**
     * Get all the supported services.
     *
     * @return a unmodifiable list of all the supported services
     */
    public static List<StreamingService> all() {
        return SERVICES;
    }
}
