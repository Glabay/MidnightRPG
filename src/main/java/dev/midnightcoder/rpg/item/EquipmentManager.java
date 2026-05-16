package dev.midnightcoder.rpg.item;

import dev.midnightcoder.rpg.ui.interfaces.EquipmentHUD;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-16
 */
public class EquipmentManager {
    private final Item[] equippedItems = new Item[EquipmentSlot.values().length];

    private final EquipmentHUD equipmentHUD;

    public EquipmentManager(EquipmentHUD equipmentHUD) {
        this.equipmentHUD = equipmentHUD;
    }

    public int getDefBonuses(int slot) {
        return (equippedItems[slot] == null) ? 0 : equippedItems[slot].getDefBuff();
    }

    public int getMagicBonuses() {
        int result = 0;

        for (int i = 0; i < equippedItems.length; i++)
            result += (equippedItems[i] == null) ? 0 : equippedItems[i].getMagicBuff();

        return result;
    }

    public int getMeleeBonuses() {
        int result = 0;

        for (int i = 0; i < equippedItems.length; i++)
            result += (equippedItems[i] == null) ? 0 : equippedItems[i].getAtkBuff();

        return result;
    }

    public int getRangeBonuses() {
        int result = 0;

        for (int i = 0; i < equippedItems.length; i++)
            result += (equippedItems[i] == null) ? 0 : equippedItems[i].getRangeBuff();

        return result;
    }

    public EquipmentHUD getEquipmentHUD() {
        return equipmentHUD;
    }

    public Item[] getEquippedItems() {
        return equippedItems;
    }

    public int getFreeSlots() {
        int total = equippedItems.length;
        for (Item equippedItem : equippedItems)
            if (equippedItem != null)
                total--;

        return total;
    }
}
