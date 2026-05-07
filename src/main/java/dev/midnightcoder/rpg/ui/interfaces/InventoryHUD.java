package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.inventory.container.Backpack;
import dev.midnightcoder.rpg.ui.container.Slot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class InventoryHUD extends Inventory  {
    private final Backpack backpack;
    private final List<Slot> slots;
    private final int rows;
    private final int cols;

    public InventoryHUD(Player player) {
        super(player);
        slots = new ArrayList<>();
        font = new Font("Arial", Font.PLAIN, 18);
        this.backpack = player.getBackpack();
        rows = 4;
        cols = 5;
        generateSlots();
        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
    }

    @Override
    protected int getInventoryIndex() {
        return BottomHUD.Tabs.INVENTORY.getSlotId();
    }

    @Override
    public void update() {
        if (visible) {
            slots.forEach(slot -> slot.update(this));
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX(), position.getY(), size.getWidth(), size.getHeight());
            slots.forEach(slot -> slot.render(renderer));
            renderer.setFont(font);
            renderer.setColor(Color.BLACK);
            var string = getSelectedGameItem() == null ? "" :
                ((Item) getSelectedGameItem()).getDefinition().name();
            renderer.renderText(string, position.getX() + 10, position.getY() + size.getHeight() - 26);
        }
    }

    private void generateSlots() {
        var padding = 8;
        var slotSize = 40;
        var spacing = slotSize + padding;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                var x = position.getX() + padding + (spacing * row) + padding;
                var y = position.getY() + padding + (spacing * col) + padding;
                slots.add(new Slot(new Vec2i(x, y), slotSize, slots.size()));
            }
        }
    }

    public boolean containsItem(Item item) {
        return slots.stream().anyMatch(slot -> slot.getItem() == item);
    }

    public int getTotalFreeSlots() {
        return Math.toIntExact(slots.stream()
            .filter(slot -> slot.getItem() == null)
            .count());
    }

    public void addItem(Item item) {
        if (backpack.addNewItem(item)) {
            var slot = backpack.findSlotForItem(item);
            if (slot >= 0) {
                log.debug("Found existing slot for item: {}", item);
                addItemToSlot(item, slot);
                return;
            }
            var slotIndex = backpack.getFirstAvailableSlot();
            if (slotIndex >= 0) {
                log.debug("Adding item to first available slot: {}", slotIndex);
                addItemToSlot(item, slotIndex);
            }
        }
    }

    public void addItemToSlot(Item item, int slotIndex) {
        if (slotIndex < 0 || slotIndex >= slots.size()) {
            log.warn("Invalid slot index: {}", slotIndex);
            return;
        }
        var slot = slots.get(slotIndex);
        if (slot.hasAnItem()) {
            log.warn("Slot {} is already occupied", slotIndex);
            return;
        }
        log.debug("Setting item {} in slot {}", item, slotIndex);
        slot.setItem(item);
    }

    public void removeItem(int itemId, int amount) {
        var optionalSlot = slots.stream()
            .filter(Slot::hasAnItem)
            .filter(slot -> slot.getItem().getDefinition().id() == itemId)
            .findFirst();
        if (optionalSlot.isPresent()) {
            var slot = optionalSlot.get();
            if (slot.getItem().decrease(amount) <= 0) {
                slot.setItem(null);
            }
        }
    }

    public void removeItemFromSlot(Slot slot) {
        if (!slot.hasAnItem()) {
            log.debug("Attempting to remove item from empty slot");
            return;
        }
        var item = slot.getItem();
        slot.setItem(null);
        log.debug("Removed item {} from slot", item.getDefinition().name());
    }

}
