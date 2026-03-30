package com.aryan.yt.engine.timeago;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface PatternsHolder {
    Map<ChronoUnit, List<String>> asMap();
    Map<ChronoUnit, Map<String, Integer>> specialCases();
    String wordSeparator();
}
