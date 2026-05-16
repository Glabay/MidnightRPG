package dev.midnightcoder.rpg.ui.container;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.ui.interfaces.ContextMenu;
import org.slf4j.Logger;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-14
 */
public class ContextOption extends Rectangle {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ContextOption.class);
    private final Vec2i position;
    private final Font optionFont;
    private final String text;
    private final int width;
    private final int index;
    private final Item selectedEntity;

    private boolean	inside = false;
    private boolean	ignorePressed = false;

    public ContextOption(Item selectedEntity, int index, int width, Vec2i position, String text) {
        this.position = position;
        this.text = text;
        this.width = width;
        this.index = index;
        this.selectedEntity = selectedEntity;
        setBounds(position.getX(), position.getY(), width, 18);
        optionFont = new Font("Arial", Font.BOLD, 14);
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
                if (panel instanceof ContextMenu) {
                    selectedEntity.handleMenuOption(text);
                }
                MidnightRPG.getInstance().getGameScreen().getContextMenu().display();
                panel.setMousePressed(false);
            }
        }
    }

    public void render(Renderer renderer) {
        var gfx2d = renderer.getGraphics2D();
        if (MidnightRPG.getInstance().getGameScreen().getContextMenu().isDisplayed()) {
            gfx2d.setColor(Color.BLACK);
            gfx2d.setFont(optionFont);
            int height = 26;
            gfx2d.drawRect(position.getX(), position.getY(), width, height);
            gfx2d.drawString(text, position.getX() + 4, position.getY() + 18);
        }
    }
}
