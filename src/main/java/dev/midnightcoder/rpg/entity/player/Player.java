package dev.midnightcoder.rpg.entity.player;

import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.system.PlayerMovement;
import dev.midnightcoder.rpg.entity.Entity;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Entity {
    private PlayerAvatar playerAvatar;

    public Player(PlayerMovement playerMovement, InputManager input) {
        playerAvatar = new PlayerAvatar(100, 100, input, playerMovement);
    }

    @Override
    public void update(double delta) {
        playerAvatar.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        playerAvatar.render(renderer);
    }
}
