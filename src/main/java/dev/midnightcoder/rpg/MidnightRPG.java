package dev.midnightcoder.rpg;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.core.Game;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.rpg.content.skills.DepletedResource;
import dev.midnightcoder.rpg.dialogue.DialogueManager;
import dev.midnightcoder.rpg.drop.DropManager;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.ground.GroundItemManager;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.NpcManager;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.item.ItemManager;
import dev.midnightcoder.rpg.save.SaveManager;
import dev.midnightcoder.rpg.scene.GameStartMode;
import dev.midnightcoder.rpg.scene.SceneManager;
import dev.midnightcoder.rpg.scene.impl.GameScreen;
import dev.midnightcoder.rpg.scene.impl.LoginScreen;
import dev.midnightcoder.rpg.ui.UIManager;

import java.util.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class MidnightRPG implements Game {
    protected final Map<DepletedResource, GameObject> depletedResources = new HashMap<>();

    private SceneManager sceneManager;
    private KeyboardInputManager input;
    private AWTMouseInputHandler mouse;
    private UIManager uiManager;
    private GameScreen gameScreen;

    protected List<Entity> entities = new ArrayList<>();
    protected List<Player> players = new ArrayList<>();
    protected List<NPC> NPCs = new ArrayList<>();

    private GameServices gameServices;

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
        gameServices = createGameServices();
        GameScreen screen = new GameScreen(uiManager, input, mouse, mode, gameServices, depletedResources);
        gameServices.dialogueManager().setGameScreen(screen);
        return screen;
    }

    private GameServices createGameServices() {
        CacheReader cacheReader = CacheReader.getInstance();
        GroundItemManager groundItemManager = new GroundItemManager();
        DropManager dropManager = new DropManager(groundItemManager);
        ItemManager itemManager = new ItemManager(cacheReader);
        NpcManager npcManager = new NpcManager(cacheReader);
        DialogueManager dialogueManager = new DialogueManager(cacheReader);
        SaveManager saveManager = new SaveManager();
        return new GameServices(
                cacheReader,
                itemManager,
                npcManager,
                dropManager,
                groundItemManager,
                dialogueManager,
                saveManager
        );
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

    public void addEntity(Entity entity) {
        if (entity instanceof NPC npc)
            NPCs.add(npc);
        else if (entity instanceof Player player)
            players.add(player);

        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        if (entity instanceof NPC npc)
            NPCs.remove(npc);
        else if (entity instanceof Player player)
            players.remove(player);
    }

    public Map<DepletedResource, GameObject> getDepletedResources() {
        return depletedResources;
    }

    public void addDepletedResource(GameObject resource, DepletedResource depletedResource) {
        depletedResources.put(depletedResource, resource);
    }
}
