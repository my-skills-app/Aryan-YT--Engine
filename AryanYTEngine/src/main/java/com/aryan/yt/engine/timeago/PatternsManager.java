package com.aryan.yt.engine.timeago;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class PatternsManager {
    private PatternsManager() {}

    public static PatternsHolder getPatterns(String languageCode, String countryCode) {
        // Return a default English patterns holder for stability
        return new PatternsHolder() {
            @Override
            public Map<ChronoUnit, List<String>> asMap() {
                Map<ChronoUnit, List<String>> map = new HashMap<>();
                map.put(ChronoUnit.SECONDS, List.of("second ago", "seconds ago"));
                map.put(ChronoUnit.MINUTES, List.of("minute ago", "minutes ago"));
                map.put(ChronoUnit.HOURS, List.of("hour ago", "hours ago"));
                map.put(ChronoUnit.DAYS, List.of("day ago", "days ago"));
                map.put(ChronoUnit.WEEKS, List.of("week ago", "weeks ago"));
                map.put(ChronoUnit.MONTHS, List.of("month ago", "months ago"));
                map.put(ChronoUnit.YEARS, List.of("year ago", "years ago"));
                return map;
            }

            @Override
            public Map<ChronoUnit, Map<String, Integer>> specialCases() {
                return Collections.emptyMap();
            }

            @Override
            public String wordSeparator() {
                return " ";
            }
        };
    }
}
