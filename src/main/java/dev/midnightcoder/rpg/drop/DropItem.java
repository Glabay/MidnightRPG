package dev.midnightcoder.rpg.drop;

import dev.midnightcoder.rpg.item.Item;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public class DropItem {
    private final int itemId;
    private final int minAmount;
    private final int maxAmount;
    private final int weight;

    public DropItem(int itemId, int minAmount, int maxAmount, int weight) {
        if (minAmount < 1) minAmount = 1;
        if (maxAmount < minAmount) maxAmount = minAmount;
        if (weight < 1) weight = 1;
        this.itemId = itemId;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.weight = weight;
    }

    public static DropItem of(int itemId, int minAmount, int maxAmount, int weight) {
        return new DropItem(itemId, minAmount, maxAmount, weight);
    }

    public static DropItem of(int itemId, int amount, int weight) {
        return new DropItem(itemId, amount, amount, weight);
    }

    public static DropItem of(int itemId, int weight) {
        return new DropItem(itemId, 1, 1, weight);
    }

    public static DropItem always(int itemId, int minAmount, int maxAmount) {
        return new DropItem(itemId, minAmount, maxAmount, 1);
    }

    public static DropItem always(int itemId, int amount) {
        return new DropItem(itemId, amount, amount, 1);
    }

    public static DropItem always(int itemId) {
        return new DropItem(itemId, 1, 1, 1);
    }

    public int rollAmount(Random random) {
        if (minAmount >= maxAmount) return minAmount;
        return minAmount + random.nextInt(maxAmount - minAmount + 1);
    }

    public Item rollItem(Random random) {
        int amount = rollAmount(random);
        return Item.of(itemId, amount);
    }

    public int getWeight() {
        return weight;
    }
}
