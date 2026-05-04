package dev.midnightcoder.rpg.entity;

import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public abstract class Entity {
    protected int worldX;
    protected int worldY;

    public void update(double delta) {}
    public void render(Renderer renderer) {}

    public int getX() {
        return worldX;
    }

    public int getY() {
        return worldY;
    }
}
