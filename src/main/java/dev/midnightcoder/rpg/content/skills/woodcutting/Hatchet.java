package dev.midnightcoder.rpg.content.skills.woodcutting;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.EquipmentSlot;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.util.ItemId;

import java.util.Arrays;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-09
 */
public enum Hatchet {
    STONE_AXE(ItemId.STONE_HATCHET, 1, 1),
    IRON_AXE(ItemId.IRON_HATCHET, 2, 5),
    STEEL_AXE(ItemId.STEEL_HATCHET, 3, 10),
    ;

    private final int hatchetId;
    private final int effectiveness;
    private final int levelRequired;

    Hatchet(int hatchetId, int effectiveness, int levelRequired) {
        this.hatchetId = hatchetId;
        this.effectiveness = effectiveness;
        this.levelRequired = levelRequired;
    }

    public static Hatchet findBestHatchet(Player player) {
        Hatchet best = null;
        for (var item : player.getBackpack().getItems()) {
            best = compare(player, item, best);
        }
        var equippedAxe = player.getEquipment().getEquippedItem(EquipmentSlot.WEAPON);
        return compare(player, equippedAxe, best);
    }

    private static Hatchet compare(Player player, Item item, Hatchet best) {
        if (item == null) return best;
        var hatchet = getHatchet(item);
        if (hatchet == null) return best;

        if (best == null)
            return hatchet;

        if (hatchet.levelRequired > best.levelRequired)
            return hatchet;

        return best;
    }

    private static Hatchet getHatchet(Item item) {
        return Arrays.stream(Hatchet.values())
            .filter(axe -> axe.hatchetId == item.getDefinition().getId())
            .findFirst()
            .orElse(null);
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}

