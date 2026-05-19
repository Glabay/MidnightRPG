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

    public int speed = 3;

    public boolean moving = false;

    public int lastX = -1;
    public int lastY = -1;

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

}
