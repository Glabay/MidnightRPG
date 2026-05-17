package dev.midnightcoder.rpg.item;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.ItemDefinition;
import dev.midnightcoder.engine.entity.item.GameItem;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.ground.GroundItem;
import dev.midnightcoder.rpg.entity.ground.GroundItemManager;
import dev.midnightcoder.rpg.ui.container.Slot;
import dev.midnightcoder.rpg.ui.interfaces.EquipmentHUD;
import dev.midnightcoder.rpg.util.MenuActionable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Item extends GameItem implements MenuActionable {
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
        if (amount >= quantity) {
            amount = quantity;
        }
        return quantity -= amount;
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
                var equipSlot = EquipmentHUD.equipmentSlots.get(getEquipSlot());
                if (equipSlot.hasAnItem())
                    removeItem(equipSlot);
                equipItem(this, slot);
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

    private void removeItem(Slot equipSlot) {
        var equipped = equipSlot.getItem();
        var backpack = MidnightRPG.getInstance().getGameScreen().getInventoryHUD();

        equipSlot.setItem(null);
        backpack.addItem(equipped);
    }

    public void handleMenuOption(String option) {
        var owner = MidnightRPG.getInstance().getGameScreen().getPlayer();
        switch (option.toLowerCase()) {
            case "equip": {
                log.info("Equipping item: {}", getDefinition().getName());
                break;
            }

            case "drink": {
                log.info("Drinking item: {}", getDefinition().getName());
                break;
            }

            case "drop": {
                var spawnPoint = new Vec2i(owner.getX() >> 5, owner.getY() >> 5);
                var groundItem = GroundItem.of(this)
                    .withOwner(owner)
                    .onMap(owner.getAvatar().getCurrentMap())
                    .at(spawnPoint);

                log.info("Player is dropping item: {}", groundItem);
                GroundItemManager.getInstance().addGroundItem(groundItem);

                MidnightRPG.getInstance().getGameScreen().getInventoryHUD()
                    .removeItem(getDefinition().getId(), getQuantity());

                break;
            }

            case "examine": {
                MidnightRPG.getInstance()
                    .getGameScreen().getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription(), "This is an example of a second line ", "This is an example of the third line");
                break;
            }

            default:
                log.warn("Unknown default option: {}", option);
                break;
        }
    }

    private void equipItem(Item item, Slot slot) {
        var equipHud = MidnightRPG.getInstance().getGameScreen().getEquipmentHUD();

        slot.setItem(null);
        equipHud.addItemToSlot(item, item.getEquipSlot());
    }

    private int getEquipSlot() {
        return definition.getEquipSlot();
    }

    @Override
    public void update(double delta) {
    }

    public int getDefBuff() {
        return 0;
    }

    public int getMagicBuff() {
        return definition.getOffMagic();
    }

    public int getAtkBuff() {
        return definition.getOffMelee();
    }

    public int getRangeBuff() {
        return definition.getOffRanged();
    }
}
