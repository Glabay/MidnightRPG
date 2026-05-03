package dev.midnightcoder.rpg.entity.player;

import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.system.PlayerMovement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.Entity;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Entity {
    private final PlayerAvatar playerAvatar;

    public Player(GameMap currentMap, InputManager input) {
        var playerMovement = new PlayerMovement(currentMap.getTileMap());
        playerAvatar = new PlayerAvatar(currentMap, input, playerMovement);
    }

    @Override
    public void update(double delta) {
        playerAvatar.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        playerAvatar.render(renderer);
    }

    public PlayerAvatar getAvatar() {
        return playerAvatar;
    }
}
