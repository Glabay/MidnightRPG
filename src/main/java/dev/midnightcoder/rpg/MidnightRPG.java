package dev.midnightcoder.rpg;

import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.scene.SceneManager;
import dev.midnightcoder.rpg.scene.impl.GameScreen;
import dev.midnightcoder.rpg.scene.impl.LoginScreen;
import dev.midnightcoder.rpg.world.TutorialIsland;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {
    private SceneManager sceneManager;
    private InputManager input;

    @Override
    public void init(InputManager input) {
        sceneManager = new SceneManager();
        this.input = input;

        sceneManager.setScene(new LoginScreen(input,
            this::startNewGame,
            this::loadGame,
            this::quitGame
        ));
    }

    @Override
    public void update(double delta) {
        sceneManager.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        sceneManager.render(renderer);
    }

    @Override
    public void shutdown() {
        System.exit(0);
    }

    private void startNewGame() {
        sceneManager.setScene(new GameScreen(input, GameStartMode.NEW_GAME));
    }

    private void loadGame() {
        sceneManager.setScene(new GameScreen(input, GameStartMode.LOAD_GAME));
    }

    private void quitGame() {
        shutdown();
    }
}
