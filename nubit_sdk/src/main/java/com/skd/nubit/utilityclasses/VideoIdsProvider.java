package com.skd.nubit.utilityclasses;

import java.util.List;
import java.util.Random;

public class VideoIdsProvider {
    private static int currentIndex = 0;

    private static final String[] videoIds = {"MN8p-Vrn6G0", "4ct3P0OCggY", "qfrocHBy6RQ", "nyd-xznCpJc", "oilnExRnFKg"};
    private static final String[] liveVideoIds = {"hHW1oY26kxQ"};
    private static final Random random = new Random();

    public static String getNextVideoId() {
        return videoIds[random.nextInt(videoIds.length)];
    }

    public static String getNextLiveVideoId() {
        return liveVideoIds[random.nextInt(liveVideoIds.length)];
    }
    public static String getNextVideoId(List<String> videoIds) {
        if (videoIds == null || videoIds.isEmpty()) {
            return null;
        }
        String nextVideoId = videoIds.get(currentIndex);
        currentIndex = (currentIndex + 1) % videoIds.size(); // Update the index for next iteration
        return nextVideoId;
    }
}
