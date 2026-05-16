package dev.midnightcoder.rpg.ui.container;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static dev.midnightcoder.rpg.util.TextUtils.getTextCentered;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public class VolumeControl extends Rectangle {
    private final Logger log = LoggerFactory.getLogger(VolumeControl.class);

    private final Vec2i position;
    private final Font font;
    private final Font subFont;

    private boolean inside = false;
    private boolean ignorePressed = false;

    public VolumeControl(Vec2i position, Vec2i size) {
        this.position = position;
        this.width = size.getWidth();
        this.height = size.getHeight();
        font = new Font("Arial", Font.BOLD, 18);
        subFont = new Font("Arial", Font.PLAIN, 14);
        setBounds(position.getX(), position.getY(), size.getWidth(), 42);
    }

    public void update(UIPanel panel) {
        var mouse = MidnightRPG.getInstance().getMouse();
        boolean leftMouseButtonDown = mouse.getButton() == MouseEvent.BUTTON1;
        if (contains(new Point(mouse.getX(), mouse.getY()))) {
            var rot = mouse.consumeWheelRotation();
            if (rot != 0)
                MidnightRPG.getInstance().getGameScreen().adjustVolume(-rot * 0.05f);

            if (!inside)
                ignorePressed = leftMouseButtonDown;
            inside = true;
            if (leftMouseButtonDown && !panel.isMousePressed() && !ignorePressed) {
                panel.setMousePressed(true);
            }
            else if (!leftMouseButtonDown && panel.isMousePressed()) {
                // Toggle Mute
                MidnightRPG.getInstance().getGameScreen().toggleMusicMute();
                panel.setMousePressed(false);
            }
        }
        else {
            if (inside) {
                panel.setSelectedItem(null);
                panel.setMousePressed(false);
            }
            inside = false;
        }
    }

    public void render(Renderer renderer) {
        var gfx2d = renderer.getGraphics2D();
            gfx2d.setColor(Color.BLACK);
            gfx2d.setFont(font);
            gfx2d.drawRect(position.getX(), position.getY(), width, 42);

        var volume = "Volume: %s".formatted(MidnightRPG.getInstance().getGameScreen().getMusicVolume());
        renderer.renderText(volume, getTextCentered(renderer, volume, position, new Vec2i(this.width, this.height)), position.getY() + 24);

        gfx2d.setFont(subFont);
        var text = "Scroll to adjust; Click to mute";
        renderer.renderText(text, getTextCentered(renderer, text, position, new Vec2i(this.width, this.height)), position.getY() + 36);
    }
}
