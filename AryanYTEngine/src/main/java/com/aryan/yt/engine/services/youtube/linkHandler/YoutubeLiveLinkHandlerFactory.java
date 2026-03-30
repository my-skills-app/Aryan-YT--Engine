package com.aryan.yt.engine.services.youtube.linkHandler;

import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.isInvidiousURL;
import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.isYoutubeURL;

import com.aryan.yt.engine.exceptions.ParsingException;
import com.aryan.yt.engine.linkhandler.ListLinkHandlerFactory;
import com.aryan.yt.engine.utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class YoutubeLiveLinkHandlerFactory extends ListLinkHandlerFactory {

    public static final String KIOSK_ID = "live";

    public static final YoutubeLiveLinkHandlerFactory INSTANCE =
            new YoutubeLiveLinkHandlerFactory();

    private static final String LIVE_CHANNEL_PATH = "/channel/UC4R8DWoMoI7CAwX8_LjQHig/livetab";
    private static final String LIVE_CHANNEL_TAB_PARAMS = "ss=CKEK";

    private YoutubeLiveLinkHandlerFactory() {
    }

    @Override
    public String getUrl(final String id,
                         final List<String> contentFilters,
                         final String sortFilter)
            throws ParsingException, UnsupportedOperationException {
        return "https://www.youtube.com" + LIVE_CHANNEL_PATH + "?" + LIVE_CHANNEL_TAB_PARAMS;
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
                && LIVE_CHANNEL_PATH.equals(urlObj.getPath())
                && LIVE_CHANNEL_TAB_PARAMS.equals(urlObj.getQuery());
    }
}
