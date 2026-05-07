package dev.midnightcoder.rpg;

import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.scene.SceneManager;
import dev.midnightcoder.rpg.scene.impl.GameScreen;
import dev.midnightcoder.rpg.scene.impl.LoginScreen;
import dev.midnightcoder.rpg.ui.UIManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {
    public static MidnightRPG instance;

    private SceneManager sceneManager;
    private KeyboardInputManager input;
    private AWTMouseInputHandler mouse;
    private UIManager uiManager;
    private GameScreen gameScreen;

    protected List<Player> players = new ArrayList<>();

    public static MidnightRPG getInstance() {
        if (instance == null) {
            instance = new MidnightRPG();
        }
        return instance;
    }

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
        gameScreen = createGameScreen(GameStartMode.NEW_GAME);
        sceneManager.setScene(gameScreen);
    }

    private void loadGame() {
        gameScreen = createGameScreen(GameStartMode.LOAD_GAME);
        sceneManager.setScene(gameScreen);
    }

    private GameScreen createGameScreen(GameStartMode mode) {
        return new GameScreen(uiManager, input, mouse, mode);
    }

    private void quitGame() {
        shutdown();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public AWTMouseInputHandler getMouse() {
        return mouse;
    }
}
