package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.entity.mob.Avatar;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class PlayerAvatar extends Avatar {
    private final KeyboardInputManager input;

    public PlayerAvatar(Vec2i position, GameMap currentMap, KeyboardInputManager input, Movement movement) {
        super(position, movement, currentMap);
        this.input = input;
        width = 32;
        height = 32;

        texture = TextureFactory.createSolidColor(width, height, Color.RED);
    }

    @Override
    public int getMoveX() {
        var dx = 0;
        if (input.isKeyHeld(KeyEvent.VK_A)) {
            dx -= moveSpeed;
            direction = Direction.WEST;
        }
        if (input.isKeyHeld(KeyEvent.VK_D)) {
            dx += moveSpeed;
            direction = Direction.EAST;
        }
        return dx;
    }

    @Override
    public int getMoveY() {
        var dy = 0;
        if (input.isKeyHeld(KeyEvent.VK_W)) {
            dy -= moveSpeed;
            direction = Direction.NORTH;
        }
        if (input.isKeyHeld(KeyEvent.VK_S)) {
            dy += moveSpeed;
            direction = Direction.SOUTH;
        }
        return dy;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    /**
     * Update movement and camera follow
     * <p>
     * Can be ignored if implementing custom movement logic
     * @param delta - time delta
     */
    @Override
    public void update(double delta) {
        super.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        if (texture != null) {
            var screenX = (int) (getX() - currentMap.getCamera().getX());
            var screenY = (int) (getY() - currentMap.getCamera().getY());
            renderer.renderTexture(texture, screenX, screenY);
        }
    }
}
