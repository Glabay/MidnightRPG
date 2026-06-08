package dev.midnightcoder.rpg.world;

import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.assets.PngMapLoader;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.impl.Chicken;
import dev.midnightcoder.rpg.entity.mob.npc.impl.Pokey;
import dev.midnightcoder.rpg.entity.mob.npc.impl.StoneGolem;
import dev.midnightcoder.rpg.entity.object.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class TutorialIsland extends GameMap {
    private final List<NPC> npcs = new ArrayList<>();
    private final List<GameObject> gameObjects = new ArrayList<>();

    public TutorialIsland() {
        IO.println("Initializing tile map for Tutorial Island");
        var mapLoader = new PngMapLoader();
        tileMap = mapLoader.loadMapFile(Regions.TUTORIAL_ISLAND.getLandscape());
        mapLoader.loadObjectMapFile(this, Regions.TUTORIAL_ISLAND.getObjectMap());

        mapWidth = tileMap.width;
        mapHeight = tileMap.height;

        initializeCamera();

        npcs.add(new Pokey(37, 52, this));
        npcs.add(new Chicken(20, 60, this));
        npcs.add(new StoneGolem(24, 64, this));
    }

    private void initializeCamera() {
        var viewWidth = WindowConfig.getWindowWidth();
        var viewHeight = WindowConfig.getWindowHeight();

        var worldWidth = mapWidth * Tile.TILE_SIZE;
        var worldHeight = mapHeight * Tile.TILE_SIZE;

        camera = new Camera2D(viewWidth, viewHeight, worldWidth, worldHeight);
    }

    @Override
    public List<NPC> getEntities() {
        return npcs;
    }

    @Override
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}
