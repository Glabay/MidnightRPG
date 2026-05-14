package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.NPCDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcManager {
    private static NpcManager instance;

    private final List<NPCDefinition> definitions;
    private final CacheReader cacheReader;

    public static NpcManager getInstance() {
        if (instance == null) {
            instance = new NpcManager();
        }
        return instance;
    }

    NpcManager() {
        this.cacheReader = CacheReader.getInstance();

        this.definitions = new ArrayList<>();

        loadDefinitions();
    }

    public NPCDefinition getDefinition(int id) {
        return definitions.stream()
                .filter(def -> def.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("NPC definition not found for ID: " + id));
    }

    public void loadDefinitions() {
        // Read out to cache manager, and look in the NPC Store for definitions
        definitions.addAll(cacheReader.getCacheManager().getNpcs());
    }
}
