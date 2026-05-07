package dev.midnightcoder.rpg.inventory.container;

import dev.midnightcoder.rpg.inventory.Inventory;
import dev.midnightcoder.rpg.item.Item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Backpack extends Inventory {

    public Backpack() {
        super(20);
    }

    public int findSlotForItem(Item item) {
        for (var index = 0; index < getItems().length; index++) {
            var invItem = getItems()[index];
            if (invItem.getDefinition().id() == item.getDefinition().id()) {
                return index;
            }
        }
        return -1;
    }
}
