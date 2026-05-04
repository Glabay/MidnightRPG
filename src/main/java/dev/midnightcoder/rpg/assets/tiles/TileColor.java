package dev.midnightcoder.rpg.assets.tiles;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public enum TileColor {
    WATER_GRASS_NORTH("0x00FF6400"),
    WATER_GRASS_WEST("0x00FF9600"),
    WATER_GRASS_NORTH_WEST("0x00FFAF00"),

    DIRT_GRASS_SOUTH("0x7F7F4000"),
    DIRT_GRASS_NORTH("0x7F7F8000"),
    DIRT_GRASS_WEST("0x7F7F1000"),
    DIRT_GRASS_EAST("0x7F7F2A00"),
    DIRT_GRASS_SOUTH_WEST("0x7F7F3A00"),
    DIRT_GRASS_SOUTH_EAST("0x7F7F5A00"),
    DIRT_GRASS_NORTH_EAST("0x7F7F6A00"),
    DIRT_GRASS_NORTH_WEST("0x7F7F7A00"),
    DIRT_GRASS_NORTH_WEST_CORNER("0x7F7F8A00"),
    DIRT_GRASS_NORTH_EAST_CORNER("0x7F7F9A00"),
    DIRT_GRASS_SOUTH_WEST_CORNER("0x7F7F0A00"),
    DIRT_GRASS_SOUTH_EAST_CORNER("0x7F7F1A00"),

    GRASS_PLAIN("0xFF00FF00"),
    STONE_WALL("0xFF7F7F7F"),
    STONE_WALL_TOP("0xFF6F6F6F"),
    WOOD_WALL("0xFF774500"),
    WOOD_WALL_TOP("0xFF603800"),
    WOOD_FLOOR("0xFF7F7F00"),
    STONE_FLOOR("0xFF7B8C7E"),
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
