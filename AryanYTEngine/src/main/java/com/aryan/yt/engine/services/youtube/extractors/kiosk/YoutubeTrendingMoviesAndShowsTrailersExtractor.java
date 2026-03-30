package com.aryan.yt.engine.services.youtube.extractors.kiosk;

import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.exceptions.ParsingException;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;

import javax.annotation.Nonnull;

public class YoutubeTrendingMoviesAndShowsTrailersExtractor
        extends YoutubeChartsBaseKioskExtractor {

    public YoutubeTrendingMoviesAndShowsTrailersExtractor(final StreamingService streamingService,
                                                          final ListLinkHandler linkHandler,
                                                          final String kioskId) {
        super(streamingService, linkHandler, kioskId, "TRENDING_MOVIES");
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        // This is the official YouTube Charts name, even if shows' trailers are returned too
        return "Trending Movie Trailers";
    }
}
