package dev.midnightcoder.rpg.drop;

import java.util.Arrays;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public enum DropRarity {
    ALWAYS(0),
    COMMON(128),
    UNCOMMON(32),
    RARE(8),
    ULTRA(1);

    private final int defaultWeight;

    DropRarity(int defaultWeight) {
        this.defaultWeight = defaultWeight;
    }

    public int getDefaultWeight() {
        return defaultWeight;
    }

    public static DropRarity fromString(String name) {
        if (name == null) return COMMON;
        var clean = name.trim().toUpperCase()
            .replace(" ", "_")
            .replace("-", "_");
        if (clean.equals("ULTRA_RARE") ||
            clean.equals("ULTRARARE")
        ) return ULTRA;

        return Arrays.stream(values())
            .filter(rarity -> rarity.name().equals(clean))
            .findFirst()
            .orElse(COMMON);
    }
}
