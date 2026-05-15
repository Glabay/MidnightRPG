package dev.midnightcoder.rpg.world;

import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.assets.PngMapLoader;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class TutorialIsland extends GameMap {

    private List<NPC> npcs = new ArrayList<>();

    public TutorialIsland() {
        IO.println("Initializing tile map for Tutorial Island");
        tileMap = new PngMapLoader().loadMapFile(Regions.TUTORIAL_ISLAND.getLandscape());

        mapWidth = tileMap.width;
        mapHeight = tileMap.height;

        initializeCamera();

        var npc = new NPC(0, new Vec2i(37 << 5, 52 << 5), this);
        npcs.add(npc);
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
}
