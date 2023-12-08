package org.example;

public final class UserProfile {
    private static String username;
    private UserProfile() {}
    public static String getUserProfile() {
        return username;
    }
    public static void setUserProfile(String username) {
        if (UserProfile.username == null) {
            UserProfile.username = username;
        }
    }
}
