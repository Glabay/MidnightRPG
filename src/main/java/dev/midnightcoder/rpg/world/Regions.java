package dev.midnightcoder.rpg.world;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public enum Regions {
    TUTORIAL_ISLAND("4a596918-0b67-4da2-b894-8e31c7621630", null),
    ;

    private final String landscape;
    private final String objectMap;

    Regions(String landscape, String objectMap) {
        this.landscape = landscape;
        this.objectMap = objectMap;
    }

    public String getLandscape() {
        return landscape;
    }

    public String getObjectMap() {
        return objectMap;
    }

}
