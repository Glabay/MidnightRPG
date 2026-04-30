package dev.midnightcoder.rpg;

import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.entity.mob.Player;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {

    private Player player;

    @Override
    public void init() {
        var redSquare = TextureFactory.createSolidColor(32, 32, Color.RED);
        player = new Player(100, 100, redSquare);
    }

    @Override
    public void update(double delta) {
        player.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        player.render(renderer);
    }

    @Override
    public void shutdown() {

    }
}
