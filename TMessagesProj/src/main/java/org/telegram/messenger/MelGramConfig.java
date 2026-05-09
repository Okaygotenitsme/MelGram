package org.telegram.messenger;

import android.content.SharedPreferences;

public class MelGramConfig {

    private static SharedPreferences prefs;

    public static boolean showGhostMode;
    public static boolean hideReadReceipts;
    public static boolean disableTypingStatus;

    public static void loadConfig() {
        prefs = ApplicationLoader.applicationContext
                .getSharedPreferences("melgramconfig", 0);
        showGhostMode = prefs.getBoolean("showGhostMode", false);
        hideReadReceipts = prefs.getBoolean("hideReadReceipts", false);
        disableTypingStatus = prefs.getBoolean("disableTypingStatus", false);
    }

    public static void setGhostMode(boolean value) {
        showGhostMode = value;
        prefs.edit().putBoolean("showGhostMode", value).apply();
    }

    public static void setHideReadReceipts(boolean value) {
        hideReadReceipts = value;
        prefs.edit().putBoolean("hideReadReceipts", value).apply();
    }

    public static void setDisableTypingStatus(boolean value) {
        disableTypingStatus = value;
        prefs.edit().putBoolean("disableTypingStatus", value).apply();
    }
}
