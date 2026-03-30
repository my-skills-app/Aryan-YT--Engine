package com.aryan.yt.engine.services.youtube;

import static com.aryan.yt.engine.StreamingService.ServiceInfo.MediaCapability.AUDIO;
import static com.aryan.yt.engine.StreamingService.ServiceInfo.MediaCapability.COMMENTS;
import static com.aryan.yt.engine.StreamingService.ServiceInfo.MediaCapability.LIVE;
import static com.aryan.yt.engine.StreamingService.ServiceInfo.MediaCapability.VIDEO;

import com.aryan.yt.engine.StreamingService;
import com.aryan.yt.engine.channel.ChannelExtractor;
import com.aryan.yt.engine.channel.tabs.ChannelTabExtractor;
import com.aryan.yt.engine.comments.CommentsExtractor;
import com.aryan.yt.engine.exceptions.ExtractionException;
import com.aryan.yt.engine.feed.FeedExtractor;
import com.aryan.yt.engine.kiosk.KioskList;
import com.aryan.yt.engine.linkhandler.LinkHandler;
import com.aryan.yt.engine.linkhandler.LinkHandlerFactory;
import com.aryan.yt.engine.linkhandler.ListLinkHandler;
import com.aryan.yt.engine.linkhandler.ListLinkHandlerFactory;
import com.aryan.yt.engine.linkhandler.ReadyChannelTabListLinkHandler;
import com.aryan.yt.engine.linkhandler.SearchQueryHandler;
import com.aryan.yt.engine.linkhandler.SearchQueryHandlerFactory;
import com.aryan.yt.engine.localization.ContentCountry;
import com.aryan.yt.engine.localization.Localization;
import com.aryan.yt.engine.playlist.PlaylistExtractor;
import com.aryan.yt.engine.search.SearchExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeChannelExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeChannelTabExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeCommentsExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeFeedExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeMixPlaylistExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeMusicSearchExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubePlaylistExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeSearchExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeStreamExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeSubscriptionExtractor;
import com.aryan.yt.engine.services.youtube.extractors.YoutubeSuggestionExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeTrendingExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeLiveExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeTrendingGamingVideosExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeTrendingMoviesAndShowsTrailersExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeTrendingMusicExtractor;
import com.aryan.yt.engine.services.youtube.extractors.kiosk.YoutubeTrendingPodcastsEpisodesExtractor;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeChannelTabLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeCommentsLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeLiveLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeTrendingGamingVideosLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeTrendingMusicLinkHandlerFactory;
import com.aryan.yt.engine.services.youtube.linkHandler.YoutubeTrendingPodcastsEpisodesLinkHandlerFactory;
import com.aryan.yt.engine.stream.StreamExtractor;
import com.aryan.yt.engine.subscription.SubscriptionExtractor;
import com.aryan.yt.engine.suggestion.SuggestionExtractor;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;

/*
 * Created by Christian Schabesberger on 23.08.15.
 *
 * Copyright (C) 2018 Christian Schabesberger <chris.schabesberger@mailbox.org>
 * YoutubeService.java is part of NewPipe Extractor.
 *
 * NewPipe Extractor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe Extractor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe Extractor.  If not, see <http://www.gnu.org/licenses/>.
 */

public class YoutubeService extends StreamingService {

    public YoutubeService(final int id) {
        super(id, "YouTube", EnumSet.of(AUDIO, VIDEO, LIVE, COMMENTS));
    }

    @Override
    public String getBaseUrl() {
        return "https://youtube.com";
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return YoutubeStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return YoutubeChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return YoutubeChannelTabLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return YoutubePlaylistLinkHandlerFactory.getInstance();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return YoutubeSearchQueryHandlerFactory.getInstance();
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler) {
        return new YoutubeStreamExtractor(this, linkHandler);
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler) {
        return new YoutubeChannelExtractor(this, linkHandler);
    }

    @Override
    public ChannelTabExtractor getChannelTabExtractor(final ListLinkHandler linkHandler) {
        if (linkHandler instanceof ReadyChannelTabListLinkHandler) {
            return ((ReadyChannelTabListLinkHandler) linkHandler).getChannelTabExtractor(this);
        } else {
            return new YoutubeChannelTabExtractor(this, linkHandler);
        }
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler) {
        if (YoutubeParsingHelper.isYoutubeMixId(linkHandler.getId())) {
            return new YoutubeMixPlaylistExtractor(this, linkHandler);
        } else {
            return new YoutubePlaylistExtractor(this, linkHandler);
        }
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler query) {
        final List<String> contentFilters = query.getContentFilters();

        if (!contentFilters.isEmpty() && contentFilters.get(0).startsWith("music_")) {
            return new YoutubeMusicSearchExtractor(this, query);
        } else {
            return new YoutubeSearchExtractor(this, query);
        }
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return new YoutubeSuggestionExtractor(this);
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        final KioskList list = new KioskList(this);
        final ListLinkHandlerFactory trendingLHF = YoutubeTrendingLinkHandlerFactory.INSTANCE;
        final ListLinkHandlerFactory runningLivesLHF =
                YoutubeLiveLinkHandlerFactory.INSTANCE;
        final ListLinkHandlerFactory trendingPodcastsEpisodesLHF =
                YoutubeTrendingPodcastsEpisodesLinkHandlerFactory.INSTANCE;
        final ListLinkHandlerFactory trendingGamingVideosLHF =
                YoutubeTrendingGamingVideosLinkHandlerFactory.INSTANCE;
        final ListLinkHandlerFactory trendingMoviesAndShowsLHF =
                YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory.INSTANCE;
        final ListLinkHandlerFactory trendingMusicLHF =
                YoutubeTrendingMusicLinkHandlerFactory.INSTANCE;

        try {
            list.addKioskEntry(
                    (streamingService, url, id) -> new YoutubeLiveExtractor(
                            YoutubeService.this,
                            runningLivesLHF.fromUrl(url),
                            id),
                    runningLivesLHF,
                    YoutubeLiveLinkHandlerFactory.KIOSK_ID
            );
            list.addKioskEntry(
                    (streamingService, url, id) -> new YoutubeTrendingPodcastsEpisodesExtractor(
                            YoutubeService.this,
                            trendingPodcastsEpisodesLHF.fromUrl(url),
                            id),
                    trendingPodcastsEpisodesLHF,
                    YoutubeTrendingPodcastsEpisodesLinkHandlerFactory.KIOSK_ID
            );
            list.addKioskEntry(
                    (streamingService, url, id) -> new YoutubeTrendingGamingVideosExtractor(
                            YoutubeService.this,
                            trendingGamingVideosLHF.fromUrl(url),
                            id),
                    trendingGamingVideosLHF,
                    YoutubeTrendingGamingVideosLinkHandlerFactory.KIOSK_ID
            );
            list.addKioskEntry(
                    (streamingService, url, id) ->
                            new YoutubeTrendingMoviesAndShowsTrailersExtractor(
                                    YoutubeService.this,
                                    trendingMoviesAndShowsLHF.fromUrl(url),
                                    id),
                    trendingMoviesAndShowsLHF,
                    YoutubeTrendingMoviesAndShowsTrailersLinkHandlerFactory.KIOSK_ID
            );
            list.addKioskEntry(
                    (streamingService, url, id) -> new YoutubeTrendingMusicExtractor(
                            YoutubeService.this,
                            trendingMusicLHF.fromUrl(url),
                            id),
                    trendingMusicLHF,
                    YoutubeTrendingMusicLinkHandlerFactory.KIOSK_ID
            );
            // Deprecated (i.e. removed from the interface of YouTube) since July 21, 2025
            list.addKioskEntry(
                    (streamingService, url, id) -> new YoutubeTrendingExtractor(
                            YoutubeService.this,
                            trendingLHF.fromUrl(url),
                            id
                    ),
                    trendingLHF,
                    YoutubeTrendingExtractor.KIOSK_ID
            );
            list.setDefaultKiosk(YoutubeLiveLinkHandlerFactory.KIOSK_ID);
        } catch (final Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return new YoutubeSubscriptionExtractor(this);
    }

    @Nonnull
    @Override
    public FeedExtractor getFeedExtractor(final String channelUrl) throws ExtractionException {
        return new YoutubeFeedExtractor(this, getChannelLHFactory().fromUrl(channelUrl));
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return YoutubeCommentsLinkHandlerFactory.getInstance();
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler urlIdHandler)
            throws ExtractionException {
        return new YoutubeCommentsExtractor(this, urlIdHandler);
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Localization
    //////////////////////////////////////////////////////////////////////////*/

    // https://www.youtube.com/picker_ajax?action_language_json=1
    private static final List<Localization> SUPPORTED_LANGUAGES = Localization.listFrom(
            "en-GB"
            /*"af", "am", "ar", "az", "be", "bg", "bn", "bs", "ca", "cs", "da", "de",
            "el", "en", "en-GB", "es", "es-419", "es-US", "et", "eu", "fa", "fi", "fil", "fr",
            "fr-CA", "gl", "gu", "hi", "hr", "hu", "hy", "id", "is", "it", "iw", "ja",
            "ka", "kk", "km", "kn", "ko", "ky", "lo", "lt", "lv", "mk", "ml", "mn",
            "mr", "ms", "my", "ne", "nl", "no", "pa", "pl", "pt", "pt-PT", "ro", "ru",
            "si", "sk", "sl", "sq", "sr", "sr-Latn", "sv", "sw", "ta", "te", "th", "tr",
            "uk", "ur", "uz", "vi", "zh-CN", "zh-HK", "zh-TW", "zu"*/
    );

    // https://www.youtube.com/picker_ajax?action_country_json=1
    private static final List<ContentCountry> SUPPORTED_COUNTRIES = ContentCountry.listFrom(
            "DZ", "AR", "AU", "AT", "AZ", "BH", "BD", "BY", "BE", "BO", "BA", "BR", "BG", "KH",
            "CA", "CL", "CO", "CR", "HR", "CY", "CZ", "DK", "DO", "EC", "EG", "SV", "EE", "FI",
            "FR", "GE", "DE", "GH", "GR", "GT", "HN", "HK", "HU", "IS", "IN", "ID", "IQ", "IE",
            "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KW", "LA", "LV", "LB", "LY", "LI", "LT",
            "LU", "MY", "MT", "MX", "ME", "MA", "NP", "NL", "NZ", "NI", "NG", "MK", "NO", "OM",
            "PK", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "PR", "QA", "RO", "RU", "SA", "SN",
            "RS", "SG", "SK", "SI", "ZA", "KR", "ES", "LK", "SE", "CH", "TW", "TZ", "TH", "TN",
            "TR", "UG", "UA", "AE", "GB", "US", "UY", "VE", "VN", "YE", "ZW"
    );

    @Override
    public List<Localization> getSupportedLocalizations() {
        return SUPPORTED_LANGUAGES;
    }

    @Override
    public List<ContentCountry> getSupportedCountries() {
        return SUPPORTED_COUNTRIES;
    }
}
