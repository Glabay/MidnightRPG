package dev.midnightcoder.rpg.entity;

import dev.midnightcoder.engine.renderer.Renderer;

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
}
