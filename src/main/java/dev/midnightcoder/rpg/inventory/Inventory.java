package dev.midnightcoder.rpg.inventory;

import dev.midnightcoder.rpg.item.Item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public abstract class Inventory {
    private final Item[] items;
    private final int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
    }

    public boolean addNewItem(Item item) {
        for (int i = 0; i < capacity; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        return false;
    }

    public Item getItemFromSlot(int slot) {
        if (slot < 0 || slot >= capacity)
            throw new IllegalArgumentException("Invalid slot index: " + slot);

        return items[slot];
    }

    public int getFirstAvailableSlot() {
        for (var index = 0; index < capacity; index++) {
            if (items[index] == null) {
                return index;
            }
        }
        return -1;
    }

    public int getCapacity() {
        return capacity;
    }

    public Item[] getItems() {
        return items;
    }
}
