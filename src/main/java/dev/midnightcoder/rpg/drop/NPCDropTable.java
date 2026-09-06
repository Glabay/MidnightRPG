package dev.midnightcoder.rpg.drop;

import dev.midnightcoder.rpg.item.Item;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the collection of weighted drop tables (Always, Common, Uncommon, Rare, Ultra)
 * for a specific NPC.
 *
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public class NPCDropTable {
    private final int npcId;
    private final Map<DropRarity, DropTable> tables = new EnumMap<>(DropRarity.class);

    public NPCDropTable(int npcId) {
        this.npcId = npcId;
        Arrays.stream(DropRarity.values())
            .forEach(rarity ->
                tables.put(rarity, new DropTable(rarity)));
    }

    public int getNpcId() {
        return npcId;
    }

    public DropTable getTable(DropRarity rarity) {
        return tables.computeIfAbsent(rarity, DropTable::new);
    }

    public Map<DropRarity, DropTable> getTables() {
        return tables;
    }

    public NPCDropTable setTableWeight(DropRarity rarity, int weight) {
        getTable(rarity).setTableWeight(weight);
        return this;
    }

    public NPCDropTable addDrop(DropRarity rarity, DropItem item) {
        getTable(rarity).addDrop(item);
        return this;
    }

    public NPCDropTable addDrop(DropRarity rarity, int itemId, int minAmount, int maxAmount, int weight) {
        getTable(rarity).addDrop(itemId, minAmount, maxAmount, weight);
        return this;
    }

    public NPCDropTable addDrop(DropRarity rarity, int itemId, int amount, int weight) {
        getTable(rarity).addDrop(itemId, amount, weight);
        return this;
    }

    public NPCDropTable addDrop(DropRarity rarity, int itemId, int weight) {
        getTable(rarity).addDrop(itemId, weight);
        return this;
    }

    public NPCDropTable addAlwaysDrop(int itemId, int minAmount, int maxAmount) {
        return addDrop(DropRarity.ALWAYS, DropItem.always(itemId, minAmount, maxAmount));
    }

    public NPCDropTable addAlwaysDrop(int itemId, int amount) {
        return addDrop(DropRarity.ALWAYS, DropItem.always(itemId, amount));
    }

    public NPCDropTable addAlwaysDrop(int itemId) {
        return addDrop(DropRarity.ALWAYS, DropItem.always(itemId));
    }

    public NPCDropTable addCommonDrop(int itemId, int minAmount, int maxAmount, int weight) {
        return addDrop(DropRarity.COMMON, itemId, minAmount, maxAmount, weight);
    }

    public NPCDropTable addCommonDrop(int itemId, int amount, int weight) {
        return addDrop(DropRarity.COMMON, itemId, amount, weight);
    }

    public NPCDropTable addCommonDrop(int itemId, int weight) {
        return addDrop(DropRarity.COMMON, itemId, weight);
    }

    public NPCDropTable addUncommonDrop(int itemId, int minAmount, int maxAmount, int weight) {
        return addDrop(DropRarity.UNCOMMON, itemId, minAmount, maxAmount, weight);
    }

    public NPCDropTable addUncommonDrop(int itemId, int amount, int weight) {
        return addDrop(DropRarity.UNCOMMON, itemId, amount, weight);
    }

    public NPCDropTable addUncommonDrop(int itemId, int weight) {
        return addDrop(DropRarity.UNCOMMON, itemId, weight);
    }

    public NPCDropTable addRareDrop(int itemId, int minAmount, int maxAmount, int weight) {
        return addDrop(DropRarity.RARE, itemId, minAmount, maxAmount, weight);
    }

    public NPCDropTable addRareDrop(int itemId, int amount, int weight) {
        return addDrop(DropRarity.RARE, itemId, amount, weight);
    }

    public NPCDropTable addRareDrop(int itemId, int weight) {
        return addDrop(DropRarity.RARE, itemId, weight);
    }

    public NPCDropTable addUltraDrop(int itemId, int minAmount, int maxAmount, int weight) {
        return addDrop(DropRarity.ULTRA, itemId, minAmount, maxAmount, weight);
    }

    public NPCDropTable addUltraDrop(int itemId, int amount, int weight) {
        return addDrop(DropRarity.ULTRA, itemId, amount, weight);
    }

    public NPCDropTable addUltraDrop(int itemId, int weight) {
        return addDrop(DropRarity.ULTRA, itemId, weight);
    }

    /**
     * Rolls drops from this NPC drop table.
     * Guaranteed ALWAYS drops are always returned.
     * In addition, one non-empty weighted table (Common, Uncommon, Rare, Ultra) is landed on
     * based on table weights, and an item from that landed table is rolled according to its item weight.
     *
     * @param random the random instance to use
     * @return the list of rolled items
     */
    public List<Item> rollDrops(Random random) {
        var drops = new ArrayList<Item>();

        // 1. Always drops
        var alwaysTable = tables.get(DropRarity.ALWAYS);
        if (alwaysTable != null && !alwaysTable.isEmpty())
            drops.addAll(alwaysTable.rollAllItems(random));

        // 2. Weighted tables
        var candidateTables = new ArrayList<DropTable>();
        int totalTableWeight = 0;
        for (var rarity : DropRarity.values()) {
            if (rarity == DropRarity.ALWAYS) continue;
            var table = tables.get(rarity);
            if (table != null && !table.isEmpty()) {
                candidateTables.add(table);
                totalTableWeight += table.getTableWeight();
            }
        }

        if (!candidateTables.isEmpty() && totalTableWeight > 0) {
            int roll = random.nextInt(totalTableWeight);
            int current = 0;
            var landedTable = candidateTables.get(candidateTables.size() - 1);
            for (var table : candidateTables) {
                current += table.getTableWeight();
                if (roll < current) {
                    landedTable = table;
                    break;
                }
            }

            var rolledItem = landedTable.rollItem(random);
            if (rolledItem != null)
                drops.add(rolledItem);
        }
        return drops;
    }

    public List<Item> rollDrops() {
        return rollDrops(ThreadLocalRandom.current());
    }
}
