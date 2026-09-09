package dev.midnightcoder.rpg.content.skills.mining;

import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.object.impl.mineable.StoneRockObject;
import dev.midnightcoder.rpg.item.ItemManager;
import dev.midnightcoder.rpg.util.ItemId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-07
 */
public enum RockType {
    STONE   (PickaxeType.STONE_PICKAXE,     ItemId.STONE,       5,      1,      1,      90),
    COPPER  (PickaxeType.STONE_PICKAXE,     ItemId.COPPER_ORE,  10,     2,      5,      90),
    TIN     (PickaxeType.STONE_PICKAXE,     ItemId.TIN_ORE,     15,     3,      10,     90),
    IRON    (PickaxeType.IRON_PICKAXE,      ItemId.IRON_ORE,    25,     4,      20,     90),
    COAL    (PickaxeType.IRON_PICKAXE,      ItemId.COAL,        30,     5,      25,     90),
    MITHRIL (PickaxeType.STEEL_PICKAXE,     ItemId.MITHRIL_ORE, 40,     6,      30,     90)
    ;

    private static final Logger log = LoggerFactory.getLogger(RockType.class);
    private final int oreId;
    private final double miningExperience;
    private final int durability;
    private final String oreName;
    private final int levelRequired;
    private final PickaxeType requiredPickaxe;
    private final int respawnTicks;

    RockType(PickaxeType requiredPickaxe, int oreId, double miningExperience, int durability, int levelRequired, int respawnTicks) {
        this.oreId = oreId;
        this.miningExperience = miningExperience;
        this.durability = durability;
        this.oreName = ItemManager.getInstance().getItemDefinition(oreId).getName();
        this.levelRequired = levelRequired;
        this.requiredPickaxe = requiredPickaxe;
        this.respawnTicks = respawnTicks;
    }

    public static RockType findRockTypeFromObject(GameObject rockObject) {
        var pickaxe = switch(rockObject) {
            case StoneRockObject _ -> STONE;
            default -> throw new IllegalStateException("Unexpected value: " + rockObject);
        };
        log.info("Finding rock type for object: {}", rockObject);
        return pickaxe;
    }

    public int getOreId() {
        return oreId;
    }

    public double getMiningExperience() {
        return miningExperience;
    }

    public int getDurability() {
        return durability;
    }

    public String getOreName() {
        return oreName;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public PickaxeType getRequiredPickaxe() {
        return requiredPickaxe;
    }

    public int getRespawnTicks() {
        return respawnTicks;
    }
}
