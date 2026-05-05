package dev.midnightcoder.rpg.assets.tiles;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public enum TileColor {
    WATER_GRASS_NORTH("0xFF00FF64"),
    WATER_GRASS_WEST("0xFF00FF96"),
    WATER_GRASS_NORTH_WEST("0xFF00FFAF"),

    DIRT_GRASS_SOUTH("0xFF7F7F40"),
    DIRT_GRASS_NORTH("0xFF7F7F80"),
    DIRT_GRASS_WEST("0xFF7F7F10"),
    DIRT_GRASS_EAST("0xFF7F7F2A"),
    DIRT_GRASS_SOUTH_WEST("0xFF7F7F3A"),
    DIRT_GRASS_SOUTH_EAST("0xFF7F7F5A"),
    DIRT_GRASS_NORTH_EAST("0xFF7F7F6A"),
    DIRT_GRASS_NORTH_WEST("0xFF7F7F7A"),
    DIRT_GRASS_NORTH_WEST_CORNER("0xFF7F7F8A"),
    DIRT_GRASS_NORTH_EAST_CORNER("0xFF7F7F9A"),
    DIRT_GRASS_SOUTH_WEST_CORNER("0xFF7F7F0A"),
    DIRT_GRASS_SOUTH_EAST_CORNER("0xFF7F7F1A"),
    DIRT_FULL("0xFF7B8C7E"),

    GRASS_PLAIN("0xFF00FF00"),
    STONE_WALL("0xFF7F7F7F"),
    STONE_WALL_TOP("0xFF6F6F6F"),
    WOOD_WALL("0xFF774500"),
    WOOD_WALL_TOP("0xFF603800"),
    WOOD_FLOOR("0xFF7F7F00"),
    GARDEN_PATCH("0xFF2A882A"),
    WATER_TILE("0xFF0000FF"),
    STONE_PATH("0xFFA0A0A0")
    ;

    private final String colorCode;

    TileColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
