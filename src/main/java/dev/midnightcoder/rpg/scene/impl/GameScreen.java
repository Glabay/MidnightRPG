package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.scene.Scene;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.assets.audio.MusicTrack;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.ui.UIManager;
import dev.midnightcoder.rpg.ui.interfaces.*;
import dev.midnightcoder.rpg.util.ItemId;
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
    private final KeyboardInputManager input;
    private final AWTMouseInputHandler mouse;
    private final UIManager uiManager;
    private final MusicTrack music;

    private GameMap currentMap;
    private Player player;

    // UI Related Objects
    private TopHUD topHUD;
    private BottomHUD bottomHUD;
    private InventoryHUD inventoryHUD;
    private SkillsHUD skillsHUD;
    private ContextMenu contextMenu;
    private AudioHUD audioHUD;

    public GameScreen(UIManager uiManager, KeyboardInputManager input, AWTMouseInputHandler mouse, GameStartMode startMode) {
        this.uiManager = uiManager;
        this.input = input;
        this.mouse = mouse;
        this.startMode = startMode;
        this.music = new MusicTrack();
    }

    @Override
    public void onLoad() {
        initializeAudioSystem();

        var isNewGame = startMode == GameStartMode.NEW_GAME;
        // First, we need to create or load the player
        if (isNewGame) startNewGame();
        else loadExistingGame();

        // next we load the Heads-up Displays
        loadHeadsUpDisplays();

        // finally, one more check if this is a new game for a starter pack
        if (isNewGame) {
            player.addItem(Item.of(ItemId.HEALTH_POTION));
            player.addItem(Item.of(ItemId.STONE_HATCHET));
            player.addItem(Item.of(ItemId.STONE_PICKAXE));
            player.addItem(Item.of(ItemId.IRON_SWORD));
            player.addItem(Item.of(ItemId.WOODEN_STAFF));
        }
    }

    @Override
    public void update(double delta) {
        // Entities - Player
        player.update(delta);

        // Entities - NPCs
        if (!currentMap.getEntities().isEmpty())
            currentMap.getEntities()
                .stream().map(e -> (NPC) e)
                .forEach(entity -> entity.update(delta));

        uiManager.update();
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

        // Entities - NetworkPlayer

        // Entities - NPCs
        if (!currentMap.getEntities().isEmpty())
            currentMap.getEntities()
                .stream().map(e -> (NPC) e)
                .forEach(entity -> entity.render(renderer));

        // Projectiles

        // Particles

        // Heads-up display
        uiManager.render(renderer);
    }

    @Override
    public void onUnload() {
        // TODO: Implement cleanup logic
        music.stop();
    }

    private void initializeAudioSystem() {
        music.loadAudioFiles();
        music.setTrack(1);
        music.play();
        music.loop();
    }

    private void loadHeadsUpDisplays() {
        // - TopBar  | Health: [===99/99===]          | CENTER HUD |              |
        topHUD = new TopHUD(player);

        // - Anything central | Equipment, bank/storage, settings
        contextMenu = new ContextMenu(player);

        // - User Interactions | Inventory, combat, spellbook, settings, music, quest skill, equipment
        // - BottomBar | Chat/Dialogue window             | [I][C][S][S][M][Q][S][E]] |
        bottomHUD = new BottomHUD(player, mouse);
        inventoryHUD = new InventoryHUD(player);
        skillsHUD = new SkillsHUD(player);
        this.audioHUD = new AudioHUD(player);

        addHeadsUpDisplay();
    }

    private void addHeadsUpDisplay() {
        uiManager.addPanel(topHUD);
        uiManager.addPanel(bottomHUD);
        uiManager.addPanel(inventoryHUD);
        uiManager.addPanel(skillsHUD);
        uiManager.addPanel(contextMenu);
        uiManager.addPanel(audioHUD);
    }

    private void startNewGame() {
        IO.println("Starting new game");
        // Generate the World
        currentMap = new TutorialIsland();
        player = new Player("Glabay", currentMap, input);
        MidnightRPG.getInstance().addEntity(player);
    }

    private void loadExistingGame() {
        IO.println("Loading existing game");
    }

    public InventoryHUD getInventoryHUD() {
        return inventoryHUD;
    }

    public SkillsHUD getSkillsHUD() {
        return skillsHUD;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public BottomHUD getBottomHUD() {
        return bottomHUD;
    }

    public TopHUD getTopHUD() {
        return topHUD;
    }

    public AudioHUD getMusicHUD() {
        return audioHUD;
    }
}
