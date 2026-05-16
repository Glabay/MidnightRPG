package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.entity.item.GameItem;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.ui.container.ContextOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-14
 */
public class ContextMenu extends UIPanel {
    private static final Logger logger = LoggerFactory.getLogger(ContextMenu.class);
    private final List<ContextOption> menuOptions = new ArrayList<>();

    private final Font menuFont;

    private Player player;
    private String[] menuOpts;
    private String title;
    private Object selectedEntity;

    private int width = 75;
    private int height;

    public ContextMenu(Player player) {
        super(new Vec2i(0, 0), new Vec2i(118, 32));
        this.player = player;
        background = TextureFactory.createFromImageFile("/ui/blank_interface.png").image();
        menuFont = new Font("Verdana", Font.ITALIC, 17);
    }

    public ContextMenu withSelectedEntity(Object entity) {
        this.selectedEntity = entity;
        if (entity instanceof GameItem item) {
            this.setSelectedItem(item);
            this.selectedEntity = item;
        }
        return this;
    }

    public ContextMenu withOptions(String[] menuOpts) {
        this.menuOpts = menuOpts;
        return this;
    }

    public ContextMenu withTitle(String title) {
        this.title = title;
        return this;
    }

    public ContextMenu setPosition(Vec2i position) {
        this.position = position;
        return this;
    }

    public void init() {
        width = Math.max((String.valueOf(title).length() * 12), 75);
        height = menuOpts.length * 32;

        if (!menuOptions.isEmpty()) menuOptions.clear();

        for (int i = 0; i < menuOpts.length; i++) {
            if (menuOpts[i] != null) {
                if (menuOpts[i].isEmpty() || menuOpts[i].isBlank())
                    continue;

                menuOptions.add(new ContextOption((Item) selectedEntity, i, width, new Vec2i(position.getX(), position.getY() + 50 + (26 * (i - 1))), menuOpts[i]));
            }
        }
    }

    public void update() {
        if (visible) {
            for (var opts : menuOptions)
                opts.update(this);
        }
    }

    public void render(Renderer renderer) {
        if (visible) {
            int x = position.getX();
            int y = position.getY();

            int padding = 26;

            // Draw the background
            renderer.getGraphics2D().drawImage(background, x, y, width, height, null);
            // Draw the title
            renderer.getGraphics2D().setColor(Color.CYAN);
            renderer.getGraphics2D().setFont(menuFont);
            renderer.getGraphics2D().drawString((title == null) ? "Title" : title, x + 8, y + 20); // title
            renderer.getGraphics2D().drawLine(x + 1, y + padding, x + width - 3, y + padding);

            for (var opts : menuOptions)
                opts.render(renderer);
        }
    }


    public boolean isDisplayed() {
        return visible;
    }

    public Vec2i getPosition() {
        return position;
    }

    public Vec2i getSize() {
        return size;
    }
}
