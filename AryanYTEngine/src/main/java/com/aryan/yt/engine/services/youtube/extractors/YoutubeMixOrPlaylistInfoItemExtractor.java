package com.aryan.yt.engine.services.youtube.extractors;

import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.extractPlaylistTypeFromPlaylistUrl;
import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.getTextFromObject;
import static com.aryan.yt.engine.services.youtube.YoutubeParsingHelper.getThumbnailsFromInfoItem;
import static com.aryan.yt.engine.utils.Utils.isNullOrEmpty;

import com.grack.nanojson.JsonObject;

import com.aryan.yt.engine.Image;
import com.aryan.yt.engine.ListExtractor;
import com.aryan.yt.engine.exceptions.ParsingException;
import com.aryan.yt.engine.playlist.PlaylistInfo;
import com.aryan.yt.engine.playlist.PlaylistInfoItemExtractor;
import com.aryan.yt.engine.services.youtube.YoutubeParsingHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class YoutubeMixOrPlaylistInfoItemExtractor implements PlaylistInfoItemExtractor {
    private final JsonObject mixInfoItem;

    public YoutubeMixOrPlaylistInfoItemExtractor(final JsonObject mixInfoItem) {
        this.mixInfoItem = mixInfoItem;
    }

    @Override
    public String getName() throws ParsingException {
        final String name = getTextFromObject(mixInfoItem.getObject("title"));
        if (isNullOrEmpty(name)) {
            throw new ParsingException("Could not get name");
        }
        return name;
    }

    @Override
    public String getUrl() throws ParsingException {
        final String url = mixInfoItem.getString("shareUrl");
        if (isNullOrEmpty(url)) {
            throw new ParsingException("Could not get url");
        }
        return url;
    }

    @Nonnull
    @Override
    public List<Image> getThumbnails() throws ParsingException {
        return getThumbnailsFromInfoItem(mixInfoItem);
    }

    @Override
    public String getUploaderName() throws ParsingException {
        // this will be a list of uploaders for mixes
        return YoutubeParsingHelper.getTextFromObject(mixInfoItem.getObject("longBylineText"));
    }

    @Override
    public String getUploaderUrl() throws ParsingException {
        // They're auto-generated, so there's no uploader
        return null;
    }

    @Override
    public boolean isUploaderVerified() throws ParsingException {
        // They're auto-generated, so there's no uploader
        return false;
    }

    @Override
    public long getStreamCount() throws ParsingException {
        final String countString = YoutubeParsingHelper.getTextFromObject(
                mixInfoItem.getObject("videoCountShortText"));
        if (countString == null) {
            throw new ParsingException("Could not extract item count for playlist/mix info item");
        }

        try {
            return Integer.parseInt(countString);
        } catch (final NumberFormatException ignored) {
            // un-parsable integer: this is a mix with infinite items and "50+" as count string
            // (though YouTube Music mixes do not necessarily have an infinite count of songs)
            return ListExtractor.ITEM_COUNT_INFINITE;
        }
    }

    @Nonnull
    @Override
    public PlaylistInfo.PlaylistType getPlaylistType() throws ParsingException {
        return extractPlaylistTypeFromPlaylistUrl(getUrl());
    }
}
