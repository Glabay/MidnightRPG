package dev.midnightcoder.rpg.content.skills;

import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.scene.impl.GameScreen;

import java.util.List;
import java.util.Map;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-06
 */
public final class DepletedResource {
    private final int refreshedId;
    private int refreshTicks;
    private final Map<DepletedResource, GameObject> depletedResources;
    private final GameScreen gameScreen;

    public DepletedResource(
        int refreshedId,
        int refreshAt,
        Map<DepletedResource, GameObject> depletedResources,
        GameScreen gameScreen
    ) {
        this.refreshedId = refreshedId;
        this.refreshTicks = refreshAt;
        this.depletedResources = depletedResources;
        this.gameScreen = gameScreen;
    }

    public void tick() {
        if (refreshTicks-- <= 0)
            restoreDepletedResource();
    }

    @SuppressWarnings("unchecked")
    private void restoreDepletedResource() {
        var resource = depletedResources.get(this);

        if (resource != null) {
            resource.getDefinition()
                .setTextureId(refreshedId);
        }
        var original = depletedResources.get(this);

        depletedResources.remove(this);

        ((List<GameObject>) gameScreen.getCurrentMap().getGameObjects())
            .add(original);

    }
}
