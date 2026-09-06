package dev.midnightcoder.rpg.drop;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.ground.GroundItem;
import dev.midnightcoder.rpg.entity.ground.GroundItemManager;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.util.ItemId;
import dev.midnightcoder.rpg.util.NpcId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public class DropManager {
    private static final Logger log = LoggerFactory.getLogger(DropManager.class);
    private static DropManager instance;

    private final Map<Integer, NPCDropTable> dropTables = new ConcurrentHashMap<>();

    public static DropManager getInstance() {
        if (instance == null) {
            instance = new DropManager();
        }
        return instance;
    }

    public DropManager() {
        initDefaultDrops();
    }

    public void register(NPCDropTable dropTable) {
        if (dropTable != null) {
            dropTables.put(dropTable.getNpcId(), dropTable);
        }
    }

    public void register(int npcId, NPCDropTable dropTable) {
        if (dropTable != null) {
            dropTables.put(npcId, dropTable);
        }
    }

    public NPCDropTable getDropTable(int npcId) {
        return dropTables.get(npcId);
    }

    public boolean hasDropTable(int npcId) {
        return dropTables.containsKey(npcId);
    }

    public List<Item> rollDrops(int npcId) {
        NPCDropTable table = getDropTable(npcId);
        if (table == null) {
            return Collections.emptyList();
        }
        return table.rollDrops();
    }

    public List<Item> rollDrops(int npcId, Random random) {
        NPCDropTable table = getDropTable(npcId);
        if (table == null) {
            return Collections.emptyList();
        }
        return table.rollDrops(random);
    }

    /**
     * Spawns ground items for the killed NPC at its tile location.
     */
    public List<GroundItem> dropItems(NPC npc, Player killer) {
        if (npc == null) return Collections.emptyList();

        var table = npc.getDropTable();
        if (table == null)
            table = getDropTable(npc.getId());

        // Redundancy check
        if (table == null)
            return Collections.emptyList();

        var items = table.rollDrops();
        var groundItems = new ArrayList<GroundItem>();
        var tilePos = new Vec2i(npc.getX() >> 5, npc.getY() >> 5);

        for (var item : items) {
            GroundItem groundItem = GroundItem.of(item)
                    .at(tilePos)
                    .onMap(npc.getCurrentMap())
                    .withOwner(killer);
            GroundItemManager.getInstance()
                .addGroundItem(groundItem);

            groundItems.add(groundItem);
            log.info("Dropped {} ({}) at {} for killer {}", item.getDefinition().getName(), item.getQuantity(), tilePos, killer != null ? killer.getProfile().getUsername() : "none");
        }

        return groundItems;
    }

    public void initDefaultDrops() {
        // Chicken
        var chickenDrops = new NPCDropTable(NpcId.CHICKEN);
            chickenDrops.addAlwaysDrop(ItemId.EMPTY_VIAL, 1);
            chickenDrops.addCommonDrop(ItemId.NORMAL_LOGS, 1, 2, 10);
            chickenDrops.addCommonDrop(ItemId.EMPTY_VIAL, 1, 1, 10);
            chickenDrops.addUncommonDrop(ItemId.HEALTH_POTION, 1, 1, 5);
            chickenDrops.addRareDrop(ItemId.STONE_HATCHET, 1, 1, 2);
            chickenDrops.addUltraDrop(ItemId.IRON_SWORD, 1, 1, 1);
        register(chickenDrops);

        // Stone Golem
        var golemDrops = new NPCDropTable(NpcId.STONE_GOLEM);
            golemDrops.addAlwaysDrop(ItemId.IRON_ORE, 1, 2);
            golemDrops.addCommonDrop(ItemId.STONE_PICKAXE, 1, 1, 10);
            golemDrops.addCommonDrop(ItemId.IRON_ORE, 2, 4, 10);
            golemDrops.addUncommonDrop(ItemId.IRON_PICKAXE, 1, 1, 5);
            golemDrops.addUncommonDrop(ItemId.HEALTH_POTION, 1, 2, 5);
            golemDrops.addRareDrop(ItemId.STEEL_PICKAXE, 1, 1, 2);
            golemDrops.addRareDrop(ItemId.IRON_SWORD, 1, 1, 2);
            golemDrops.addUltraDrop(ItemId.STEEL_HATCHET, 1, 1, 1);
        register(golemDrops);
    }
}
