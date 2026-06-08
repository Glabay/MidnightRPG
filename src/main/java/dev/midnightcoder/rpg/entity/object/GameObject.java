package dev.midnightcoder.rpg.entity.object;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.ObjectDefinition;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-20
 */
public abstract class GameObject extends Entity {
    private static final Logger log = LoggerFactory.getLogger(GameObject.class);
    private final Vec2i position;
    private final GameMap currentMap;

    private ObjectDefinition definition;
    private BufferedImage image;

    protected GameObject(GameMap currentMap, Vec2i position) {
        this.currentMap = currentMap;
        this.position = position;
        getImageForObject();
        this.worldX = position.getX() * Tile.TILE_SIZE;
        this.worldY = position.getY() * Tile.TILE_SIZE;
        this.width = Tile.TILE_SIZE;
        this.height = Tile.TILE_SIZE;
    }

    protected abstract int getObjectId();

    public Vec2i getPosition() {
        return position;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        var screenX = (int) (worldX - currentMap.getCamera().getX());
        var screenY = (int) (worldY - currentMap.getCamera().getY());
        renderer.renderImage(image, screenX, screenY);
    }

    @Override
    public void handleMenuOption(String option) {
        var player = MidnightRPG.getInstance().getGameScreen().getPlayer();
        switch (option.toLowerCase()) {
            case "mine" -> {
                // if the user is too far, send a dialogue message
                if (!entityWithinDist(this, 2)) {
                    MidnightRPG.getInstance()
                        .getGameScreen()
                        .getDialogueInterface()
                        .sendInfoInter("Too far away", "You are too far away to interact with this.");
                    return;
                }
                log.info("Player is within range, attempting to mine object");
            }
            case "examine" -> {
                MidnightRPG.getInstance()
                    .getGameScreen()
                    .getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
            }
            default ->
                throw new IllegalArgumentException("Invalid menu option: " + option);
        }
    }

    private void getImageForObject() {
        var cacheReader = CacheReader.getInstance();
        this.definition = cacheReader.getCacheManager().getObjects().get(getObjectId());
        var textureId = definition.getTextureId();
        var cachedSpriteSheet = cacheReader.getCacheManager().getTextures().get(textureId);
        var spriteSheetId = cachedSpriteSheet.getSpriteSheetId();
        var spriteSheetFrame = cachedSpriteSheet.getFrameIndex();
        var spriteSheet = cacheReader.getCacheManager().getSpriteSheets().get(spriteSheetId);
        var cachedSpriteIndex = spriteSheet.getSpriteId();
        var cachedSprite = CacheReader.getInstance().getTexture(cachedSpriteIndex);

        var spritePosition = new Vec2i(spriteSheetFrame % spriteSheet.getCols(), spriteSheetFrame / spriteSheet.getCols());
        image = getTextureFromSpriteSheet(cachedSprite.image(), spritePosition).image();
    }

    private Texture getTextureFromSpriteSheet(BufferedImage spriteSheet, Vec2i spritePosition) {
        return TextureFactory.createTextureFromSpriteSheet(spriteSheet, Tile.TILE_SIZE, spritePosition);
    }

    public ObjectDefinition getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "GameObject{" +
            "position=" + position +
            ", definition=" + definition +
            '}';
    }
}
