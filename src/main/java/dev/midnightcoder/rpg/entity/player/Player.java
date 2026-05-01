package dev.midnightcoder.rpg.entity.player;

import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.Entity;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Entity {
    private PlayerAvatar playerAvatar;

    public Player(GameMap currentMap, InputManager input) {
        // Temp Texture
        var redSquare = TextureFactory.createSolidColor(32, 32, Color.RED);
        var playerMovement = new Movement(currentMap.getTileMap());

        playerAvatar = new PlayerAvatar(100, 100, redSquare, input, playerMovement);
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
