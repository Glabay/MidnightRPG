package dev.midnightcoder.rpg.entity.mob.player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class PlayerProfile {
    private final String username;

    public PlayerProfile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
