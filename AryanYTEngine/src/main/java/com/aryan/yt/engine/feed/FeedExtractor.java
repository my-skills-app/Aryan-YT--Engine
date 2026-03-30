package com.aryan.yt.engine.feed;

import com.aryan.yt.engine.ListExtractor;
import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;
import com.aryan.yt.engine.stream.StreamInfoItem;

/**
 * This class helps to extract items from lightweight feeds that the services may provide.
 * <p>
 * YouTube is an example of a service that has this alternative available.
 */
public abstract class FeedExtractor extends ListExtractor<StreamInfoItem> {
    public FeedExtractor(final StreamingService service, final ListLinkHandler listLinkHandler) {
        super(service, listLinkHandler);
    }
}
