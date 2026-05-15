package dev.midnightcoder.rpg.item;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.ItemDefinition;
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

    public Item(int itemId, int quantity) {
        this.definition = CacheReader.getInstance().getItemDefinition(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + itemId));
        this.quantity = quantity;
    }

    public static Item of(int itemId) {
        return of(itemId, 1);
    }

    public static Item of(int itemId, int quantity) {
        return new Item(itemId, quantity);
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
        return CacheReader.getInstance().getTexture(definition.getSpriteId());
    }

    public String getItemDescription() {
        return getDefinition().getDescription();
    }

    public String getItemDefaultAction() {
        if (getDefinition().getBackpackActions()[0] != null ||
            getDefinition().getBackpackActions()[0].equalsIgnoreCase("null")
        ) return getDefinition().getBackpackActions()[0];

        // return the "use" option
        return getDefinition().getBackpackActions()[1];
    }

    public void handleDefaultOption(Slot slot, String option) {
        switch (option.toLowerCase()) {
            case "equip": {
                log.info("Equipping item: {}", getDefinition().getName());
                break;
            }

            case "drink": {
                log.info("Drinking item: {}", getDefinition().getName());
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
