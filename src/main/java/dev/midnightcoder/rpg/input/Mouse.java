package dev.midnightcoder.rpg.input;

import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;

import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public class Mouse extends AWTMouseInputHandler {

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = MouseEvent.NOBUTTON;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

}
