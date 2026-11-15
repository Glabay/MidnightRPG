package dev.midnightcoder.rpg;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.rpg.dialogue.DialogueManager;
import dev.midnightcoder.rpg.drop.DropManager;
import dev.midnightcoder.rpg.entity.ground.GroundItemManager;
import dev.midnightcoder.rpg.entity.mob.npc.NpcManager;
import dev.midnightcoder.rpg.item.ItemManager;
import dev.midnightcoder.rpg.save.SaveManager;

/**
 * Immutable hub that bundles all shared game services.
 * Created once during {@link MidnightRPG#init()} and passed through constructors
 * so business logic classes never call {@code getInstance()} on singletons.
 */
public record GameServices(
        CacheReader cacheReader,
        ItemManager itemManager,
        NpcManager npcManager,
        DropManager dropManager,
        GroundItemManager groundItemManager,
        DialogueManager dialogueManager,
        SaveManager saveManager
) {
}
