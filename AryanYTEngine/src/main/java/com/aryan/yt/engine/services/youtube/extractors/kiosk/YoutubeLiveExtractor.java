package com.aryan.yt.engine.services.youtube.extractors.kiosk;

import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;

public class YoutubeLiveExtractor extends YoutubeDesktopBaseKioskExtractor {

    public YoutubeLiveExtractor(final StreamingService streamingService,
                                final ListLinkHandler linkHandler,
                                final String kioskId) {
        super(streamingService, linkHandler, kioskId, "UC4R8DWoMoI7CAwX8_LjQHig",
                "EgdsaXZldGFikgEDCKEK");
    }
}
