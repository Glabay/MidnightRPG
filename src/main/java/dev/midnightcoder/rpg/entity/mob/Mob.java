package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Mob extends Entity {
    protected final int moveSpeed = 1;

    protected int speed = 3;

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(double delta) {}

}
