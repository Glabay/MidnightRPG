package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.entity.mob.Avatar;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class PlayerAvatar extends Avatar {
    private final Map<Direction, BufferedImage[]> animatedFrames = new EnumMap<>(Direction.class);
    private final KeyboardInputManager input;

    public AnimatedSprite animatedSprite = new AnimatedSprite(0.15);

    public PlayerAvatar(Vec2i position, GameMap currentMap, KeyboardInputManager input, Movement movement) {
        super(position, 32, 32, movement, currentMap);
        this.input = input;

        texture = TextureFactory.createSolidColor(width, height, Color.RED);
    }

    @Override
    public int getMoveX() {
        var dx = 0;
        if (input.isKeyHeld(KeyEvent.VK_A)) {
            dx -= moveSpeed;
            direction = Direction.WEST;
            animatedSprite.setFrames(animatedFrames.get(direction));
        }
        if (input.isKeyHeld(KeyEvent.VK_D)) {
            dx += moveSpeed;
            direction = Direction.EAST;
            animatedSprite.setFrames(animatedFrames.get(direction));
        }
        return dx;
    }

    @Override
    public int getMoveY() {
        var dy = 0;
        if (input.isKeyHeld(KeyEvent.VK_W)) {
            dy -= moveSpeed;
            direction = Direction.NORTH;
            animatedSprite.setFrames(animatedFrames.get(direction));
        }
        if (input.isKeyHeld(KeyEvent.VK_S)) {
            dy += moveSpeed;
            direction = Direction.SOUTH;
            animatedSprite.setFrames(animatedFrames.get(direction));
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

    public BufferedImage[] getAnimatedFrames(Direction direction) {
        return animatedFrames.get(direction);
    }

    public void setAnimatedFrames(Direction direction, BufferedImage[] frames) {
        animatedFrames.put(direction, frames);
    }

}
