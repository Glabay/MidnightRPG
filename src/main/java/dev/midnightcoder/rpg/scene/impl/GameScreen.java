package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.scene.Scene;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.assets.audio.MusicTrack;
import dev.midnightcoder.rpg.entity.ground.GroundItemManager;
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
    private final GroundItemManager groundItemManager;

    private GameMap currentMap;
    private Player player;

    // UI Related Objects
    private TopHUD topHUD;
    private BottomHUD bottomHUD;
    private InventoryHUD inventoryHUD;
    private SkillsHUD skillsHUD;
    private ContextMenu contextMenu;
    private AudioHUD audioHUD;
    private EquipmentHUD equipmentHUD;
    private boolean mousePressed;

    public GameScreen(UIManager uiManager, KeyboardInputManager input, AWTMouseInputHandler mouse, GameStartMode startMode) {
        this.uiManager = uiManager;
        this.input = input;
        this.mouse = mouse;
        this.startMode = startMode;
        this.music = new MusicTrack();
        this.groundItemManager = GroundItemManager.getInstance();
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

        // Ground items
        groundItemManager.update(delta);

        // UI
        uiManager.update();

        handleEntityClicks();
    }

    private void handleEntityClicks() {
        var mouseB = mouse.getButton();
        if (mouseB == java.awt.event.MouseEvent.NOBUTTON) {
            mousePressed = false;
            return;
        }

        if (mousePressed) return; // Wait for release

        // Check if clicking on UI
        for (UIPanel panel : uiManager.getPanels()) {
            if (panel.isVisible()) {
                int mx = mouse.getX();
                int my = mouse.getY();
                int px = panel.position.getX();
                int py = panel.position.getY();
                int pw = panel.getSize().getX();
                int ph = panel.getSize().getY();
                if (mx >= px && mx <= px + pw && my >= py && my <= py + ph) {
                    return; // Clicking on UI, don't check entities
                }
            }
        }

        // Convert mouse screen coordinates to world coordinates
        int worldX = (int) (mouse.getX() + currentMap.getCamera().getX());
        int worldY = (int) (mouse.getY() + currentMap.getCamera().getY());

        // Check NPCs
        for (Object e : currentMap.getEntities()) {
            NPC npc = (NPC) e;
            if (npc.contains(worldX, worldY)) {
                onEntityClicked(npc, mouseB);
                mousePressed = true;
                return;
            }
        }

        // Check Ground Items
        for (dev.midnightcoder.rpg.entity.ground.GroundItem item : groundItemManager.getGroundItems()) {
            if (item.contains(worldX, worldY)) {
                onEntityClicked(item, mouseB);
                mousePressed = true;
                return;
            }
        }

        // Check Player
        if (player.contains(worldX, worldY)) {
            onEntityClicked(player, mouseB);
            mousePressed = true;
            return;
        }
    }

    private void onEntityClicked(dev.midnightcoder.rpg.entity.Entity entity, int button) {
        if (button == java.awt.event.MouseEvent.BUTTON3) { // Right click
            var menuOpts = new java.util.ArrayList<String>();
            if (entity instanceof NPC) {
                menuOpts.add("Examine");
            } else if (entity instanceof dev.midnightcoder.rpg.entity.ground.GroundItem) {
                menuOpts.add("Take");
                menuOpts.add("Examine");
            } else if (entity instanceof Player) {
                menuOpts.add("Stats");
            }

            contextMenu.setPosition(new dev.midnightcoder.engine.util.Vec2i(mouse.getX(), mouse.getY()))
                .withTitle("Choose Option")
                .withSelectedEntity(entity)
                .withOptions(menuOpts.toArray(new String[0]))
                .init();
            contextMenu.display();
        } else if (button == java.awt.event.MouseEvent.BUTTON1) { // Left click
            player.setSelectedEntity(entity);
        }
    }

    @Override
    public void render(Renderer renderer) {
        // Landscape map
        if (Objects.nonNull(currentMap))
            currentMap.renderTileMap(renderer);

        // Object map

        // Ground items
        groundItemManager.render(renderer);

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
        music.setTrack("magnus-opus");
        music.play();
        music.loop();
    }

    public void setMusicTrack(String trackName) {
        music.stop();
        music.setTrack(trackName);
        music.play();
        music.loop();
    }

    public String getMusicVolume() {
        var vol = music.getVolume();
        return "%d%%".formatted((int) (vol * 100));
    }

    private void loadHeadsUpDisplays() {
        // - TopBar  | Health: [===99/99===]          | CENTER HUD |              |
        topHUD = new TopHUD(player);

        // - Anything central | Equipment, bank/storage, settings
        contextMenu = new ContextMenu(player);
        equipmentHUD = new EquipmentHUD(player);

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
        uiManager.addPanel(equipmentHUD);
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

    public EquipmentHUD getEquipmentHUD() {
        return equipmentHUD;
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

    public void toggleMusicMute() {
        music.mute();
    }

    public void adjustVolume(float v) {
        music.adjustVolume(v);
    }

    public Player getPlayer() {
        return player;
    }
}
