package dev.midnightcoder.rpg.item;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.ItemDefinition;
import dev.midnightcoder.rpg.assets.CacheDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-14
 */
public class ItemManager implements CacheDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ItemManager.class);

    private static ItemManager instance;

    private final List<ItemDefinition> itemDefinitions;
    private final CacheReader cacheReader;

    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    ItemManager() {
        logger.info("Initializing ItemManager");
        this.cacheReader = CacheReader.getInstance();

        this.itemDefinitions = new ArrayList<>();

        loadDefinitions();
    }

    public ItemDefinition getItemDefinition(int id) {
        return itemDefinitions.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item definition with ID " + id + " not found"));
    }

    @Override
    public void loadDefinitions() {
        logger.info("Loading item definitions from cache");
        this.itemDefinitions.addAll(cacheReader.getCacheManager().getItems());
        logger.info("Loaded {} item definitions", itemDefinitions.size());
    }
}
