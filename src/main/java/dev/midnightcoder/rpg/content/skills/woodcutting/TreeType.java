package dev.midnightcoder.rpg.content.skills.woodcutting;

import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.MapleTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.NormalTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.OakTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.WillowTreeObject;
import dev.midnightcoder.rpg.util.ItemId;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-09
 */
public enum TreeType {
    NORMAL_TREE (Hatchet.STONE_AXE, ItemId.NORMAL_LOGS,  "normal logs",     5,   1, 1,   120),
    OAK_TREE    (Hatchet.STONE_AXE, ItemId.OAK_LOGS,     "oak logs",        10,  2, 5,   150),
    MAPLE_TREE  (Hatchet.STEEL_AXE, ItemId.MAPLE_LOGS,   "maple logs",      15,  3, 10,  180),
    WILLOW_TREE (Hatchet.STEEL_AXE, ItemId.WILLOW_LOGS,  "willow logs",     20,  4, 20,  210)
    ;

    private final Hatchet hatchet;
    private final int logId;
    private final String logName;
    private final int experience;
    private final int durability;
    private final int levelRequired;
    private final int respawnTicks;


    TreeType(Hatchet hatchet, int logId, String logName, int experience, int durability, int levelRequired, int respawnTicks) {
        this.hatchet = hatchet;
        this.logId = logId;
        this.logName = logName;
        this.experience = experience;
        this.durability = durability;
        this.levelRequired = levelRequired;
        this.respawnTicks = respawnTicks;
    }

    public static TreeType findTreeFromObject(GameObject gameObject) {
        return switch (gameObject) {
            case NormalTreeObject _ -> NORMAL_TREE;
            case OakTreeObject _ -> OAK_TREE;
            case MapleTreeObject _ -> MAPLE_TREE;
            case WillowTreeObject _ -> WILLOW_TREE;
            default ->
                throw new IllegalArgumentException("Unknown tree type");
        };
    }

    public Hatchet getHatchet() {
        return hatchet;
    }

    public int getLogId() {
        return logId;
    }

    public String getLogName() {
        return logName;
    }

    public int getExperience() {
        return experience;
    }

    public int getDurability() {
        return durability;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getRespawnTicks() {
        return respawnTicks;
    }
}
