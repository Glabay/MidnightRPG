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
        // TODO: add multiple UI/HUD
        //      - TopBar  | USERNAME      | CENTER HUD |       Health: 99/99  |
        //      - Anything central | Equipment, bank/storage, settings
        //      - User Interactions | Inventory, mini-setting, spellbook, community, skill
        //      - BottomBar | Chat/Dialogue window             |  Interactive tabs here  |
    }

    @Override
    public void update(double delta) {
        // TODO: Implement game update logic
        player.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        // Landscape map
        if (Objects.nonNull(currentMap))
            currentMap.renderTileMap(renderer);

        // Object map

        // Entities - Player
        if (Objects.nonNull(player))
            player.render(renderer);

        // Entities - NPCs

        // Projectiles

        // Particles

        // Heads-up display
    }

    @Override
    public void onUnload() {
        // TODO: Implement cleanup logic
    }

    private void startNewGame() {
        IO.println("Starting new game");
        // Generate the World
        currentMap = new TutorialIsland();
        player = new Player(currentMap, input);

    }

    private void loadExistingGame() {
        IO.println("Loading existing game");
    }
}
