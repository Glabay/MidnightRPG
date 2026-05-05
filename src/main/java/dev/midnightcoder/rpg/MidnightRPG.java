package dev.midnightcoder.rpg;

import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.scene.SceneManager;
import dev.midnightcoder.rpg.scene.impl.GameScreen;
import dev.midnightcoder.rpg.scene.impl.LoginScreen;
import dev.midnightcoder.rpg.ui.UIManager;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {
    private SceneManager sceneManager;
    private KeyboardInputManager input;
    private AWTMouseInputHandler mouse;
    private UIManager uiManager;

    @Override
    public void init(KeyboardInputManager input, AWTMouseInputHandler mouse) {
        sceneManager = new SceneManager();
        uiManager = new UIManager();

        this.input = input;
        this.mouse = mouse;

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
        sceneManager.setScene(new GameScreen(uiManager, input, mouse, GameStartMode.NEW_GAME));
    }

    private void loadGame() {
        sceneManager.setScene(new GameScreen(uiManager, input, mouse, GameStartMode.LOAD_GAME));
    }

    private void quitGame() {
        shutdown();
    }
}
