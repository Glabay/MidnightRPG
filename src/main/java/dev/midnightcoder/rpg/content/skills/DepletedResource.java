package dev.midnightcoder.rpg.content.skills;

import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.object.GameObject;

import java.util.List;

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

    public DepletedResource(
        int refreshedId,
        int refreshAt
    ) {
        this.refreshedId = refreshedId;
        this.refreshTicks = refreshAt;
    }

    public void tick() {
        if (refreshTicks-- <= 0)
            restoreDepletedResource();
    }

    @SuppressWarnings("unchecked")
    private void restoreDepletedResource() {
        var resource = MidnightRPG.getInstance()
            .getDepletedResources()
            .get(this);

        if (resource != null) {
            resource.getDefinition()
                .setTextureId(refreshedId);
        }
        var original = MidnightRPG.getInstance()
            .getDepletedResources()
            .get(this);

        MidnightRPG.getInstance()
            .getDepletedResources()
            .remove(this);

        ((List<GameObject>) MidnightRPG.getInstance()
            .getGameScreen()
            .getCurrentMap()
            .getGameObjects())
            .add(original);

    }
}
