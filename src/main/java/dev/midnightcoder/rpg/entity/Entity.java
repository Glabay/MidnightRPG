package dev.midnightcoder.rpg.entity;

import dev.midnightcoder.engine.renderer.Renderer;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.util.MenuActionable;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public abstract class Entity implements MenuActionable {
    protected int worldX;
    protected int worldY;
    protected int width;
    protected int height;

    public void update(double delta) {}
    public void render(Renderer renderer) {}

    public int getX() {
        return worldX;
    }

    public int getY() {
        return worldY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(int x, int y) {
        return x >= getX() && x <= getX() + getWidth() &&
               y >= getY() && y <= getY() + getHeight();
    }

    public void handleMenuOption(String option) {
        // Default implementation
    }

    protected boolean entityWithinDist(Entity entity, int distance) {
        var player = MidnightRPG.getInstance().getGameScreen().getPlayer();
        var dist = Vec2i.getDistance(
            new Vec2i(player.getAvatar().getX(), player.getAvatar().getY()),
            new Vec2i(entity.getX(), entity.getY())
        );
        return dist <= (distance << 5);
    }
}
