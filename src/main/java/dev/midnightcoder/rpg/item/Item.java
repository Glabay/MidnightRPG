package dev.midnightcoder.rpg.item;

import dev.midnightcoder.engine.entity.item.GameItem;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.rpg.ui.container.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Item extends GameItem {
    private static final Logger log = LoggerFactory.getLogger(Item.class);
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

    public int decrease(int amount) {
        if (amount > quantity) {
            amount = quantity;
        }
        quantity -= amount;
        return amount;
    }

    public ItemDefinition getDefinition() {
        return definition;
    }

    public Texture getIcon() {
        return texture;
    }

    public String getItemDescription() {
        return getDefinition().description();
    }

    public String getItemDefaultAction() {
        if (getDefinition().inventoryOptions()[0] != null ||
            getDefinition().inventoryOptions()[0].equalsIgnoreCase("null")
        ) return getDefinition().inventoryOptions()[0];

        // return the "use" option
        return getDefinition().inventoryOptions()[1];
    }

    public void handleDefaultOption(Slot slot, String option) {
        switch (option.toLowerCase()) {
            case "equip": {
                log.info("Equipping item: {}", getDefinition().name());
                break;
            }

            case "drink": {
                log.info("Drinking item: {}", getDefinition().name());
                break;
            }

            default:
                log.warn("Unknown default option: {}", option);
                break;
        }
    }


    @Override
    public void update(double delta) {
    }
}
