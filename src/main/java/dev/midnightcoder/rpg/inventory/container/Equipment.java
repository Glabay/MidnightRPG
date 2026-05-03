package dev.midnightcoder.rpg.inventory.container;

import dev.midnightcoder.rpg.inventory.Item;
import dev.midnightcoder.rpg.item.EquipmentSlot;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Equipment {
    private final Map<EquipmentSlot, Item> equippedItems = new EnumMap<>(EquipmentSlot.class);

    public void equipItem(EquipmentSlot slot, Item item) {
        equippedItems.put(slot, item);
    }

    public boolean unequipItem(EquipmentSlot slot) {
        if (equippedItems.get(slot) == null) {
            return false;
        }
        return equippedItems.remove(slot) != null;
    }

    public Item getEquippedItem(EquipmentSlot slot) {
        return equippedItems.get(slot);
    }
}
