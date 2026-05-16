package dev.midnightcoder.rpg.entity.ground;

import dev.midnightcoder.engine.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-16
 */
public class GroundItemManager {
    private static final Logger log = LoggerFactory.getLogger(GroundItemManager.class);
    private static GroundItemManager instance;

    private final List<GroundItem> groundItems;

    public static GroundItemManager getInstance() {
        if (instance == null) {
            instance = new GroundItemManager();
        }
        return instance;
    }

    GroundItemManager() {
        groundItems = new ArrayList<>();
    }

    public void update(double delta) {
        groundItems.forEach(item -> item.update(delta));

        removeExpiredGroundItems();
    }

    private void removeExpiredGroundItems() {
        var totalRemoved = 0;
        var iterator = groundItems.iterator();

        while (iterator.hasNext()) {
            var groundItem = iterator.next();

            if (groundItem.isExpired()) {
                iterator.remove();
                totalRemoved++;
            }
        }

        if (totalRemoved > 0)
            log.info("Removed {} expired ground items", totalRemoved);
    }

    public void render(Renderer renderer) {
        groundItems.forEach(item -> item.render(renderer));
    }

    public void addGroundItem(GroundItem item) {
        groundItems.add(item);
    }

    public void removeGroundItem(GroundItem item) {
        groundItems.remove(item);
    }

    public List<GroundItem> getGroundItems() {
        return groundItems;
    }

}
