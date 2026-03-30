package com.aryan.yt.engine.services.youtube.extractors.kiosk;

import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;

public class YoutubeTrendingPodcastsEpisodesExtractor extends YoutubeDesktopBaseKioskExtractor {

    public YoutubeTrendingPodcastsEpisodesExtractor(final StreamingService streamingService,
                                                    final ListLinkHandler linkHandler,
                                                    final String kioskId) {
        super(streamingService, linkHandler, kioskId, "FEpodcasts_destination", "qgcCCAM%3D");
    }
}
