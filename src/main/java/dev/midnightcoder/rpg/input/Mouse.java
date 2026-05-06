package dev.midnightcoder.rpg.input;

import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public class Mouse extends AWTMouseInputHandler {
    public static Mouse instance;

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    public Point getPosition() {
        return new Point(getX(), getY());
    }

}
