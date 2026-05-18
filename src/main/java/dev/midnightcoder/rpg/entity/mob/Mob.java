package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.rpg.entity.Entity;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Mob extends Entity {
    protected final int moveSpeed = 1;

    protected int speed = 3;

    protected boolean moving = false;

    protected int lastX = -1;
    protected int lastY = -1;

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(double delta) {}

}
