package dev.midnightcoder.rpg.entity.mob.player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class PlayerProfile {
    /// TODO: Export this profile and data to a file for saving and loading
    ///       This will be temp for "Offline-Mode" until we introduce a network
    ///       At that point a DTO will be made, and passed over the network for saving

    private final String username;

    private float masterVolume = 0.5f;

    public PlayerProfile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        this.masterVolume = masterVolume;
    }
}
