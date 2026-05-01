package dev.midnightcoder.rpg;

import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.world.TutorialIsland;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {
    private GameMap currentMap;
    private PlayerAvatar player;

    @Override
    public void init(InputManager input) {
        currentMap = new TutorialIsland();
        currentMap.initTileMap();
        var redSquare = TextureFactory.createSolidColor(32, 32, Color.RED);
        player = new PlayerAvatar(100, 100, redSquare, input, new Movement(currentMap.getTileMap()));
    }

    @Override
    public void update(double delta) {
        player.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        currentMap.renderTileMap(renderer);
        player.render(renderer);
    }

    @Override
    public void shutdown() {

    }
}
