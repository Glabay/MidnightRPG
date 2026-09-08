package dev.midnightcoder.rpg.content.skills.mining;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.item.EquipmentSlot;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.util.ItemId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-07
 */
public enum PickaxeType {
    STONE_PICKAXE(ItemId.STONE_PICKAXE, 1, 1),
    IRON_PICKAXE(ItemId.IRON_PICKAXE, 2, 5),
    STEEL_PICKAXE(ItemId.STEEL_PICKAXE, 3, 10)
    ;

    private final int pickaxeId;
    private final int effectiveness;
    private final int levelRequired;

    PickaxeType(int pickaxeId, int effectiveness, int levelRequired) {
        this.pickaxeId = pickaxeId;
        this.effectiveness = effectiveness;
        this.levelRequired = levelRequired;
    }

    public static PickaxeType findBestPickaxe(Player player) {
        PickaxeType best = null;
        // Check the inventory
        for (var item : player.getBackpack().getItems())
            best = compare(player, item, best);

        var weaponHand = player.getEquipment().getEquippedItem(EquipmentSlot.WEAPON);
        // Check the best inventory pickaxe to the equipped item
        return compare(player, weaponHand, best);
    }

    private static PickaxeType compare(Player player, Item item, PickaxeType best) {
        if (item == null)
            return best;

        var pickaxe = getPickaxeFromItem(item);
        if (pickaxe == null)
            return best;

        var miningLevel = player.getSkillSet().getSkill(SkillType.MINING).getLevel();
        if (miningLevel < pickaxe.levelRequired)
            return best;

        if (best == null)
            return pickaxe;

        if (pickaxe.levelRequired < best.getLevelRequired())
            return best;

        return pickaxe;
    }

    private static PickaxeType getPickaxeFromItem(Item item) {
        return Arrays.stream(PickaxeType.values())
            .filter(pick -> pick.pickaxeId == item.getDefinition().getId())
            .findFirst()
            .orElse(null);
    }

    public int getPickaxeId() {
        return pickaxeId;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}
