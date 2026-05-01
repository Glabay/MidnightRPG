package dev.midnightcoder.rpg.entity;

import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public abstract class Entity {
    public void update(double delta) {}
    public void render(Renderer renderer) {}
}
