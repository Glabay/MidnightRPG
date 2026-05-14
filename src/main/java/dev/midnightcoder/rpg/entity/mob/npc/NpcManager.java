package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.NPCDefinition;
import dev.midnightcoder.rpg.assets.CacheDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcManager implements CacheDefinition {
    private static final Logger logger = LoggerFactory.getLogger(NpcManager.class);
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
        logger.info("Initializing NpcManager");
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

    @Override
    public void loadDefinitions() {
        logger.info("Loading NPC definitions from cache");
        definitions.addAll(cacheReader.getCacheManager().getNpcs());
        logger.info("Loaded {} NPC definitions", definitions.size());
    }
}
