package dev.midnightcoder.rpg.item;

import dev.midnightcoder.engine.entity.item.GameItem;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.rpg.ui.container.Slot;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Item extends GameItem {
    private final ItemDefinition definition;
    private int quantity;

    public Item(ItemDefinition definition, int quantity) {
        this.definition = definition;
        this.quantity = quantity;
    }

    /**
     *
     * @param amount - Amount to add to the item stack
     * @return The amount that was left after adding to the item stack
     */
    public int add(int amount) {
        if ((long) quantity + amount > Integer.MAX_VALUE) {
            quantity = Integer.MAX_VALUE;
            var total = (long) quantity + amount;
            return Math.toIntExact(total - Integer.MAX_VALUE);
        }
        quantity += amount;
        return 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemDefinition getDefinition() {
        return definition;
    }

    public Texture getIcon() {
        return icon;
    }

    public String getItemDefaultAction() {
        return "";
    }

    public void handleDefaultOption(Slot slot, String option) {

    }


}
