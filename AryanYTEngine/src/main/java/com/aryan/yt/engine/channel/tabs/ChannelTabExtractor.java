package com.aryan.yt.engine.channel.tabs;

import com.aryan.yt.engine.InfoItem;
import com.aryan.yt.engine.ListExtractor;
import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;

import javax.annotation.Nonnull;

/**
 * A {@link ListExtractor} of {@link InfoItem}s for tabs of channels.
 */
public abstract class ChannelTabExtractor extends ListExtractor<InfoItem> {

    protected ChannelTabExtractor(@Nonnull final StreamingService service,
                                  @Nonnull final ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public String getName() {
        return getLinkHandler().getContentFilters().get(0);
    }
}
