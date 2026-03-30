package com.aryan.yt.engine;

import com.aryan.yt.engine.exceptions.ParsingException;

import javax.annotation.Nonnull;
import java.util.List;

public interface InfoItemExtractor {
    String getName() throws ParsingException;
    String getUrl() throws ParsingException;
    @Nonnull
    List<Image> getThumbnails() throws ParsingException;
}
