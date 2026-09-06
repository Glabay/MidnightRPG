package dev.midnightcoder.rpg.drop;

import dev.midnightcoder.rpg.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Represents a table of drop items for a specific rarity tier (Always, Common, Uncommon, Rare, Ultra).
 * Has a table weight indicating the chance to land on this table when rolling non-guaranteed drops.
 *
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public class DropTable {
    private final DropRarity rarity;
    private int tableWeight;
    private final List<DropItem> items = new ArrayList<>();

    public DropTable(DropRarity rarity, int tableWeight) {
        this.rarity = rarity != null ? rarity : DropRarity.COMMON;
        this.tableWeight = Math.max(1, tableWeight);
    }

    public DropTable(DropRarity rarity) {
        this(rarity, rarity != null ? Math.max(1, rarity.getDefaultWeight()) : 1);
    }

    public DropTable addDrop(DropItem dropItem) {
        if (dropItem != null)
            items.add(dropItem);
        return this;
    }

    public DropTable addDrop(int itemId, int minAmount, int maxAmount, int weight) {
        return addDrop(new DropItem(itemId, minAmount, maxAmount, weight));
    }

    public DropTable addDrop(int itemId, int amount, int weight) {
        return addDrop(new DropItem(itemId, amount, amount, weight));
    }

    public DropTable addDrop(int itemId, int weight) {
        return addDrop(new DropItem(itemId, 1, 1, weight));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public int getTotalItemWeight() {
        return items.stream()
            .mapToInt(DropItem::getWeight)
            .sum();
    }

    public DropItem rollDropItem(Random random) {
        if (items.isEmpty()) {
            return null;
        }
        if (items.size() == 1) {
            return items.getFirst();
        }
        int totalWeight = getTotalItemWeight();
        if (totalWeight <= 0) {
            return items.getFirst();
        }
        int roll = random.nextInt(totalWeight);
        int current = 0;
        for (DropItem item : items) {
            current += item.getWeight();
            if (roll < current)
                return item;
        }
        return items.getLast();
    }

    public DropItem rollDropItem() {
        return rollDropItem(ThreadLocalRandom.current());
    }

    public Item rollItem(Random random) {
        var dropItem = rollDropItem(random);
        return dropItem != null ? dropItem.rollItem(random) : null;
    }

    public List<Item> rollAllItems(Random random) {
        return items.stream()
            .map(dropItem -> dropItem.rollItem(random))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public int getTableWeight() {
        return tableWeight;
    }

    public void setTableWeight(int tableWeight) {
        this.tableWeight = Math.max(1, tableWeight);
    }
}
