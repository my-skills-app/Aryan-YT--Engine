package com.aryan.yt.engine.services.youtube.linkHandler;

import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.isInvidiousURL;
import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.isYoutubeURL;

import com.aryan.yt.engine.exceptions.ParsingException;
import com.aryan.yt.engine.linkhandler.ListLinkHandlerFactory;
import com.aryan.yt.engine.utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class YoutubeTrendingGamingVideosLinkHandlerFactory extends ListLinkHandlerFactory {

    public static final String KIOSK_ID = "trending_gaming";

    public static final YoutubeTrendingGamingVideosLinkHandlerFactory INSTANCE =
            new YoutubeTrendingGamingVideosLinkHandlerFactory();

    private YoutubeTrendingGamingVideosLinkHandlerFactory() {
    }

    @Override
    public String getUrl(final String id,
                         final List<String> contentFilters,
                         final String sortFilter)
            throws ParsingException, UnsupportedOperationException {
        return "https://www.youtube.com/gaming/trending";
    }

    @Override
    public String getId(final String url) throws ParsingException, UnsupportedOperationException {
        return KIOSK_ID;
    }

    @Override
    public boolean onAcceptUrl(final String url) {
        final URL urlObj;
        try {
            urlObj = Utils.stringToURL(url);
        } catch (final MalformedURLException e) {
            return false;
        }

        return Utils.isHTTP(urlObj) && (isYoutubeURL(urlObj) || isInvidiousURL(urlObj))
                && "/gaming/trending".equals(urlObj.getPath());
    }
}
