package dev.midnightcoder.rpg.ui.container;

import dev.midnightcoder.cache.model.AudioDefinition;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.ui.interfaces.ContextMenu;
import dev.midnightcoder.rpg.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public class AudioContainer extends Rectangle {
    private final Logger log = LoggerFactory.getLogger(AudioContainer.class);

    private final Vec2i position;
    private final AudioDefinition definition;
    private final Font trackFont;
    private final Color fontColor;
    private final UIPanel parent;
    private final boolean unlocked;

    private boolean inside = false;
    private boolean ignorePressed = false;

    public AudioContainer(UIPanel panel, AudioDefinition definition, boolean unlocked) {
        this.parent = panel;
        this.position = new Vec2i(
            parent.position.getX() + 6,
            parent.position.getY() + 56 + (24 * definition.getId())
        );
        this.width = parent.getSize().getWidth() - 14;
        this.definition = definition;
        this.unlocked = unlocked;
        trackFont = new Font("Arial", Font.PLAIN, 16);
        fontColor = unlocked ? Color.GREEN : Color.GRAY;
        setBounds(position.getX(), position.getY(), width, 18);
    }

    public void update(UIPanel panel) {
        var mouse = MidnightRPG.getInstance().getMouse();
        boolean leftMouseButtonDown = mouse.getButton() == MouseEvent.BUTTON1;
        if (contains(new Point(mouse.getX(), mouse.getY()))) {
            if (!inside)
                ignorePressed = leftMouseButtonDown;
            inside = true;
            if (leftMouseButtonDown && !panel.isMousePressed() && !ignorePressed) {
                panel.setMousePressed(true);
            }
            else if (!leftMouseButtonDown && panel.isMousePressed()) {
                log.debug("Player is interacting with audio container: {}", definition.getName());
                MidnightRPG.getInstance().getGameScreen().setMusicTrack(definition.getName());
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
        gfx2d.setColor(fontColor);
        gfx2d.setFont(trackFont);
        int height = 26;
        gfx2d.drawRect(position.getX(), position.getY(), width, height);
        gfx2d.drawString(definition.getName(), parent.getTextCentered(renderer, definition.getName()), position.getY() + 18);
    }
}
