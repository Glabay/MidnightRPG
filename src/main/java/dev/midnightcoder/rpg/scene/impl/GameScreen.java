package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.scene.Scene;
import dev.midnightcoder.engine.system.PlayerMovement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.player.Player;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.world.TutorialIsland;

import java.util.Objects;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class GameScreen extends Scene {
    private final GameStartMode startMode;
    private final InputManager input;

    private GameMap currentMap;
    private Player player;

    public GameScreen(InputManager input, GameStartMode startMode) {
        this.input = input;
        this.startMode = startMode;
    }

    @Override
    public void onLoad() {
        if (startMode == GameStartMode.NEW_GAME)
            startNewGame();
        else if (startMode == GameStartMode.LOAD_GAME)
            loadExistingGame();
    }

    @Override
    public void update(double delta) {
        // TODO: Implement game update logic
        player.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        // TODO: Implement rendering logic
        if (Objects.nonNull(currentMap))
            currentMap.renderTileMap(renderer);

        if (Objects.nonNull(player))
            player.render(renderer);
    }

    @Override
    public void onUnload() {
        // TODO: Implement cleanup logic
    }

    private void startNewGame() {
        IO.println("Starting new game");

        /* TODO:
         * Create fresh player
         * Create/load initial world
         * Spawn player at starting position
         */
        currentMap = new TutorialIsland();


        var playerMovement = new PlayerMovement(currentMap.getTileMap());

        player = new Player(playerMovement, input);

    }

    private void loadExistingGame() {
        IO.println("Loading existing game");

        /* TODO:
         * Read save file
         * Restore player state
         * Restore world state
         * Spawn player at saved position
         */
    }
}
