package dev.midnightcoder.rpg.entity.ground;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.Item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-16
 */
public class GroundItem extends Entity {
    private static final int DEFAULT_TTL_SECONDS = 120 * 60;

    private final Item item;

    private Vec2i position;
    private Player owner;
    private GameMap map;

    private int TTL;
    private boolean isExpired = false;

    public GroundItem(Item item) {
        this.item = item;
        this.TTL = DEFAULT_TTL_SECONDS;
        this.width = 32;
        this.height = 32;
    }

    public static GroundItem of(Item item) {
        return new GroundItem(item);
    }

    public GroundItem withOwner(Player owner) {
        this.owner = owner;
        return this;
    }

    public GroundItem onMap(GameMap map) {
        this.map = map;
        return this;
    }

    public GroundItem at(Vec2i position) {
        this.position = position;
        this.worldX = position.getX() * Tile.TILE_SIZE;
        this.worldY = position.getY() * Tile.TILE_SIZE;
        return this;
    }

    @Override
    public void update(double delta) {
        if (isExpired)
            return;
        if (TTL-- <= 0)
            isExpired = true;
    }

    @Override
    public void render(Renderer renderer) {
        if (!isExpired && map != null) {
            var screenX = (int) (worldX - map.getCamera().getX());
            var screenY = (int) (worldY - map.getCamera().getY());
            renderer.renderImage(item.getIcon().image(), screenX, screenY);
        }
    }

    public Item getItem() {
        return item;
    }

    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public void handleMenuOption(String option) {
        var player = dev.midnightcoder.rpg.MidnightRPG.getInstance().getGameScreen().getPlayer();
        switch (option.toLowerCase()) {
            case "pick-up", "take" -> {
                player.addItem(item);
                isExpired = true; // Mark for removal
            }
            case "examine" -> {
                IO.println(item.getItemDescription());
            }
        }
    }

    @Override
    public String toString() {
        return "GroundItem{" +
                "item=" + item.getDefinition().getName() +
                ", position=" + position +
                ", owner=" + owner.getProfile().getUsername() +
                ", map=" + map +
                ", TTL=" + TTL +
                ", isExpired=" + isExpired +
                '}';
    }
}
